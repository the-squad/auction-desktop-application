/*
 * The MIT License
 *
 * Copyright 2017 Contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package models;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;

/**
 *
 * @param <T> Model
 */
public abstract class Model<T extends Model> {

    private static Connection conn;

    private static void getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = Database.getInstance().getConnection();
            }
        } catch (SQLException ex) {
            conn = Database.getInstance().getConnection();
        }
    }

    public static <T extends Model> List<T> find(Class<T> clazz) {
        List<T> elements = Model.find(clazz, "");
        if (elements == null || elements.isEmpty()) {
            return null;
        } else {
            return elements;
        }
    }

    public static <T extends Model> List<T> find(Class<T> clazz, String Criteria, Object... Params) {
        try {
            getConnection();
            T model = clazz.newInstance();
            String sql = "SELECT * FROM " + model.getTable() + (Criteria.isEmpty() ? "" : " WHERE " + Criteria);
            PreparedStatement Statement = conn.prepareStatement(sql);
            for (int i = 0; i < Params.length; i++) {
                String paramType = Params[i].getClass().getTypeName();
                if (paramType.equals("int") || paramType.equals(Integer.class.getName())) {
                    Statement.setInt(i + 1, (int) Params[i]);
                } else if (paramType.equals(String.class.getName())) {
                    Statement.setString(i + 1, (String) Params[i]);
                }
            }
            if (!Statement.execute()) {
                return null;
            }
            ResultSet resultSet = Statement.getResultSet();
            List<T> Elements = new ArrayList<>();
            List<Field> fields = model.getFields();
            fields.forEach(f -> f.setAccessible(true));
            while (resultSet.next()) {
                model = clazz.newInstance();
                for (Field field : fields) {
                    String fieldName = field.getName().substring(1);
                    String fieldType = field.getType().getTypeName();
                    if (fieldType.equals("int") || fieldType.equals(Integer.class.getName())) {
                        field.setInt(model, resultSet.getInt(fieldName));
                    } else if (fieldType.equals(String.class.getName())) {
                        field.set(model, resultSet.getString(fieldName));
                    } else if (fieldType.equals((new byte[]{}).getClass().getTypeName())) {
                        field.set(model, resultSet.getBytes(field.getName().substring(1)));
                    } else {
                        throw new Error("Undefined datatype");
                    }
                }
                Elements.add(model);
            }
            return Elements;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }

    }

    public static <T extends Model> T find(Class<T> clazz, int ID) {
        List<T> elements = Model.find(clazz, "ID = ?", ID);
        if (elements == null || elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }

    public boolean save() {
        try {
            getConnection();
            String kies = "";
            HashMap<String, Object> FieldsWithValues = getFieldsWithValues();
            kies = FieldsWithValues
                    .keySet()
                    .stream()
                    .filter(key -> !key.equals("id"))
                    .map(key -> "`" + key + "` = ?, ")
                    .reduce(kies, String::concat)
                    .replaceFirst(",\\s$", "");
            String sql = "UPDATE `" + this.getTable() + "` SET " + kies + "WHERE `id` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            fill(statement);
            statement.setInt(FieldsWithValues.size(), (int) FieldsWithValues.get("id"));
            return statement.executeUpdate() >= 1;
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean create() {
        try {
            getConnection();
            String kies = " (", values = " (";
            for (Map.Entry<String, Object> entry : getFieldsWithValues().entrySet()) {
                if (!entry.getKey().toLowerCase().equals("id")) {
                    kies += "`" + entry.getKey() + "`, ";
                    values += "?, ";
                }
            }
            kies = kies.substring(0, kies.length() - 2) + ")";
            values = values.substring(0, values.length() - 2) + ")";
            String sql = "INSERT INTO " + this.getTable() + kies + " VALUES" + values;
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            fill(statement);
            if (statement.executeUpdate() < 1) {
                return false;
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Field id = getFields().get(0);
                if (!id.isAccessible()) {
                    id.setAccessible(true);
                }
                id.setInt(this, resultSet.getInt(1));
                return true;
            }
        } catch (SQLException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    private void fill(PreparedStatement statement) {
        int counter = 1;
        List<Field> fields = getFields();
        for (Map.Entry<String, Object> entry : getFieldsWithValues().entrySet()) {
            if (!entry.getKey().toLowerCase().equals("id")) {
                Object fieldValue = entry.getValue();
                String fieldType;
                if (fieldValue == null) {
                    fieldType = fields.get(counter).getType().getTypeName();
                } else {
                    fieldType = entry.getValue().getClass().getTypeName();
                }
                try {
                    if (fieldType.equals("int") || fieldType.equals(Integer.class.getName())) {
                        statement.setInt(counter, (int) fieldValue);
                    } else if (fieldType.equals(String.class.getName())) {
                        statement.setString(counter, (String) fieldValue);
                    } else if (fieldType.equals((new byte[]{}).getClass().getTypeName())) {
                        statement.setBytes(counter, (byte[]) fieldValue);
                    } else if (fieldType.equals("double") || fieldType.equals(Double.class.getName())) {
                        statement.setDouble(counter, (double) fieldValue);
                    } else if (fieldType.equals(java.util.Date.class.getName())) {
                        statement.setDate(counter, (java.sql.Date) fieldValue);
                    } else {
                        throw new Error("Undefined datatype");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                counter++;
            }
        }
    }

    public static <T extends Model> boolean delete(Class<T> clazz, int id) {
        try {
            T model = clazz.newInstance();
            getConnection();
            String sql = "DELETE FROM `" + model.getTable() + "` WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    protected String getTable() {
        return getClass()
                .getSimpleName()
                .replaceAll("y$", "ie")
                .replaceAll("(.)([A-Z])", "$1_$2")
                .toLowerCase() + "s";
    }

    protected List<Field> getFields() {
        return Arrays
                .asList(getClass().getDeclaredFields())
                .stream()
                .filter((field) -> field.getName().startsWith("_"))
                .collect(Collectors.toList());
    }

    protected HashMap<String, Object> getFieldsWithValues() {
        HashMap<String, Object> data = new HashMap<>();
        getFields()
                .stream()
                .map(field -> {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    return field;
                })
                .forEach(field -> {
                    try {
                        data.put(field.getName().substring(1), field.get(this));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                });
        return data;
    }

    @Override
    public String toString() {
        return "Model{" + '}';
    }
    
    
}
