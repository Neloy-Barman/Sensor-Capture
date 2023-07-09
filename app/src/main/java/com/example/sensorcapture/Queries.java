package com.example.sensorcapture;

public class Queries {

    public static String x_value = "time";
    public static String y_value = "sensor_value";

    public static String light = "light";
    public static String proximity = "proximity";
    public static String acce_x = "accelerometer_x";
    public static String acce_y = "accelerometer_y";
    public static String acce_z = "accelerometer_z";

    public static String gyro_x = "gyroscope_x";
    public static String gyro_y = "gyroscope_y";
    public static String gyro_z = "gyroscope_z";

    public static String create_light = create_query(light);
    public static String create_proximity = create_query(proximity);

    public static String create_acc_x = create_query(acce_x);
    public static String create_acc_y = create_query(acce_y);
    public static String create_acc_z = create_query(acce_z);

    public static String create_gyro_x = create_query(gyro_x);
    public static String create_gyro_y = create_query(gyro_y);
    public static String create_gyro_z = create_query(gyro_z);

    public static  String drop_light = drop_query(light);

    public static  String drop_proximity =drop_query(proximity);

    public static String drop_acc_x = drop_query(acce_x);
    public static String drop_acc_y = drop_query(acce_y);
    public static String drop_acc_z = drop_query(acce_z);

    public static String drop_gyro_x = drop_query(gyro_x);
    public static String drop_gyro_y = drop_query(gyro_y);
    public static String drop_gyro_z = drop_query(gyro_z);

    public static String create_query(String table_name)
    {
        return "create table "+table_name+"("+x_value+" REAL, "+y_value+" REAL);";
    }
    public static String drop_query(String table_name)
    {
        return "drop table if exists "+table_name+";";
    }


}
