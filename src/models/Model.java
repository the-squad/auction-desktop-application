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
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;

/**
 * Model class :D
 *
 * @param <T> Model
 */
public abstract class Model<T extends Model> {

//<editor-fold defaultstate="collapsed" desc="Database connections">
    private static Connection connection;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Database Operations">
    
//<editor-fold defaultstate="collapsed" desc="Static functions">
    
    /**
     * Get all record of selected class
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @return List of All records from database, Or null if there is no
     * records.
     */
    public static <T extends Model> List<T> find(Class<T> clazz) {
        List<T> elements = Model.find(clazz, "");
        if (elements == null || elements.isEmpty()) {
            return null;
        } else {
            return elements;
        }
    }
    
    /**
     * Get record of selected class by ID
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @param ID Record ID
     * @return Selected record from database, Or null if id not exist.
     */
    public static <T extends Model> T find(Class<T> clazz, int ID) {
        List<T> elements = Model.find(clazz, "ID = ?", ID);
        if (elements == null || elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }
    
    /**
     * Get all record of selected class
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @param Criteria SQL Conditions and values replaced with ?
     * @param Params Values of ?
     * <h4>Example:</h4>
     * <code>Model.find(User.class, "`Name` LIKE ? OR `UserTypeID` > ?", "M*", 5)</code>
     * @return List of Selected records from database, Or null if there is
     * Errors.
     */
    public static <T extends Model> List<T> find(Class<T> clazz, String Criteria, Object... Params) {
        try {
            getConnection();
            T model = clazz.newInstance();
            String sql = "SELECT * FROM " + model.getTable() + (Criteria.isEmpty() ? "" : " WHERE " + Criteria);
            PreparedStatement Statement = generateQuery(sql, Params);
            Statement.execute();
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
                    } else if (fieldType.equals("double") || fieldType.equals(Double.class.getName())) {
                        field.setDouble(model, resultSet.getDouble(fieldName));
                    } else if (fieldType.equals(String.class.getName())) {
                        field.set(model, resultSet.getString(fieldName));
                    } else if (fieldType.equals((new byte[]{}).getClass().getTypeName())) {
                        field.set(model, resultSet.getBytes(field.getName().substring(1)));
                    } else if (fieldType.equals(java.util.Date.class.getName())) {
                        java.sql.Date sqlDate = resultSet.getDate(field.getName().substring(1));
                        field.set(model, new java.util.Date(sqlDate.getTime()));
                    } else {
                        throw new Error("Undefined datatype");
                    }
                }
                Elements.add(model);
            }
            return Elements;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SQLException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
        
    }
    
    /**
     * Delete record of selected class by ID
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @param id Record ID
     * @return List of Selected records from database, Or null if there is
     * Errors.
     */
    public static <T extends Model> boolean delete(Class<T> clazz, int id) {
        try {
            T model = clazz.newInstance();
            getConnection();
            String sql = "DELETE FROM `" + model.getTable() + "` WHERE id = ?";
            PreparedStatement statement = generateQuery(sql, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException | IllegalAccessException | InstantiationException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
    
    /**
     ** Get records count from database.
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @return Record count, or -1 if there is an error.
     */
    public static <T extends Model> int count(Class<T> clazz) {
        return count(clazz, "");
    }
    
    /**
     * Get records count from database.
     *
     * @param <T> Database model (Not required)
     * @param clazz Selected class
     * @param Criteria SQL Conditions and values replaced with ?
     * @param Params Values of ?
     * <h4>Example:</h4>
     * <code>Model.count(User.class, "`Name` LIKE ? OR `UserTypeID` > ?", "M*", 5)</code>
     * @return Record count, or -1 if there is an error.
     */
    public static <T extends Model> int count(Class<T> clazz, String Criteria, Object... Params) {
        try {
            String sql = String.format("SELECT count(*) FROM `%S` %S %S",
                    clazz.newInstance().getTable(),
                    (Criteria.isEmpty() ? "" : " WHERE "),
                    Criteria);
            PreparedStatement statement = generateQuery(sql, Params);
            statement.execute();
            ResultSet set = statement.getResultSet();
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return -1;
    }
    
    /**
     * Generate a Prepared Statement from SQL query with its values
     *
     * @param sql SQL Query
     * @param Params SQL values
     * <h4>Example:</h4>
     * <code>Model.generateQuery("SELECT * FROM users WHERE id = ?", 5);</code>
     * @return
     */
    public static PreparedStatement generateQuery(String sql, Object... Params) {
        try {
            getConnection();
            PreparedStatement Statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < Params.length; i++) {
                String paramType = Params[i].getClass().getTypeName();
                boolean isArray = Params[i].getClass().isArray();
                if (isArray) {
                    paramType = Params[i].getClass().getComponentType().getTypeName();
                    if (!("byte".equals(paramType))) {
                        return null;
                    }
                }
                //Int
                if (paramType.equals("int") || paramType.equals(Integer.class.getName())) {
                    Statement.setInt(i + 1, (int) Params[i]);
                    //Byte
                } else if (paramType.equals("byte") || paramType.equals(Byte.class.getName())) {
                    if (isArray) {
                        Statement.setBytes(i + 1, (byte[]) Params[i]);
                    } else {
                        Statement.setByte(i + 1, (byte) Params[i]);
                    }
                    //Double
                } else if (paramType.equals("double") || paramType.equals(Double.class.getName())) {
                    Statement.setDouble(i + 1, (double) Params[i]);
                    //Long
                } else if (paramType.equals("long") || paramType.equals(Long.class.getName())) {
                    Statement.setLong(i + 1, (long) Params[i]);
                    //Float
                } else if (paramType.equals("float") || paramType.equals(Float.class.getName())) {
                    Statement.setFloat(i + 1, (float) Params[i]);
                    //String
                } else if (paramType.equals(String.class.getName())) {
                    Statement.setString(i + 1, (String) Params[i]);
                    //Date
                } else if (paramType.equals(java.util.Date.class.getName())) {
                    java.util.Date javaDate = (java.util.Date) Params[i];
                    Statement.setTimestamp(i + 1, new Timestamp(javaDate.getTime()));
                } else {
                    Logger.getGlobal().log(Level.WARNING, "Unknown datatype: {0}", paramType);
                    Statement.setObject(i + 1, Params[i]);
                }
                
            }
            return Statement;
        } catch (SQLException ex) {
            Logger.getGlobal().log(Level.WARNING, ex.getMessage(), ex);
        }
        return null;
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Local functions">
    /**
     * Save Class changes into database.
     *
     * @return true if changes saved, false otherwise.
     */
    public boolean save() {
        try {
            getConnection();
            String Keys = "";
            HashMap<String, Object> FieldsWithValues = getFieldsWithValues();
            Keys = FieldsWithValues
                    .keySet()
                    .stream()
                    .filter(key -> !key.equals("id"))
                    .map(key -> "`" + key + "` = ?, ")
                    .reduce(Keys, String::concat)
                    .replaceFirst(",\\s$", "");
            String sql = "UPDATE `" + this.getTable() + "` SET " + Keys + "WHERE `id` = ?";
            List<Object> values = getValuesWithoutID();
            values.add((int) FieldsWithValues.get("id"));
            PreparedStatement statement = generateQuery(sql, values.toArray());
            return statement.executeUpdate() >= 1;
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
    
    /**
     * Create new record from object and set its id.
     *
     * @return return true if record saved, false otherwise.
     */
    public boolean create() {
        try {
            getConnection();
            String Keys = " (", values = " (";
            for (Map.Entry<String, Object> entry : getFieldsWithValues().entrySet()) {
                if (!entry.getKey().toLowerCase().equals("id")) {
                    Keys += "`" + entry.getKey() + "`, ";
                    values += "?, ";
                }
            }
            Keys = Keys.substring(0, Keys.length() - 2) + ")";
            values = values.substring(0, values.length() - 2) + ")";
            String sql = "INSERT INTO " + this.getTable() + Keys + " VALUES" + values;
            PreparedStatement statement = generateQuery(sql, getValuesWithoutID().toArray());
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
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
//</editor-fold>
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Helper functions">

    /**
     * Get Connection from database class.
     */
    private static void getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = Database.getInstance().getConnection();
            }
        } catch (SQLException ex) {
            connection = Database.getInstance().getConnection();
        }
    }

    /**
     * Generate list of all values without id.
     *
     * @return Generated list.
     */
    private List getValuesWithoutID() {
        return getFieldsWithValues()
                .entrySet()
                .stream()
                .filter(t -> !t.getKey().equals("id"))
                .map((t) -> t.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Get table name from class name.
     *
     * @return Table name.
     */
    protected String getTable() {
        return getClass()
                .getSimpleName()
                .replaceAll("y$", "ie")
                .replaceAll("(.)([A-Z])", "$1_$2")
                .toLowerCase() + "s";
    }

    /**
     * Get all fields from selected class.
     *
     * @return List of class fields.
     */
    protected List<Field> getFields() {
        return Arrays
                .asList(getClass().getDeclaredFields())
                .stream()
                .filter((field) -> field.getName().startsWith("_"))
                .collect(Collectors.toList());
    }

    /**
     * Get fields name with its values
     *
     * @return HashMap&lt;Field name, Field value&gt;
     */
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
                        Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
                    }
                });
        return data;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + getFieldsWithValues()
                .entrySet()
                .stream()
                .map(t -> t.getKey() + " = " + t.getValue())
                .reduce("{", (t, u) -> t + u + ", ")
                .replaceFirst("..$", "") + "}";
    }
//</editor-fold>
    
}
