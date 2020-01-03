package com.majiang.user.majianguser.utils;
import com.majiang.user.majianguser.bean.UserInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author banruo
 *
 */
//@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public final class BeanUtils {
    private static  final     SimpleDateFormat SIMPLE_DATE_FORMAT= new SimpleDateFormat("YYYYMMddHHmmss");
    /**
     * 构造函数.
     *
     */
    private BeanUtils() {
        throw new RuntimeException("This is util class,can not instance");
    }


    /**
     * 验证手机号码是否佛和规定
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 利用反射设置指定类公共属性的值
     * @param obj
     */
    public  static synchronized void setXXX(Object obj){
        Class cls = obj.getClass();
        try {
            Method setKeyID = cls.getDeclaredMethod("setKeyID",String.class);
            Method setAddTime = cls.getDeclaredMethod("setAddTime",Date.class);
            Method setModifTime = cls.getDeclaredMethod("setModifTime",Date.class);
            Method setIsDelete = cls.getDeclaredMethod("setIsDelete",Integer.class);
           System.out.println(setKeyID.getName()+""+setAddTime);
           setKeyID.invoke(obj,SIMPLE_DATE_FORMAT.format(new Date()));
           setIsDelete.invoke(obj,0);
           setAddTime.invoke(obj,new Date());
           setModifTime.invoke(obj,new Date());
            System.out.println("设置公共属性完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        setXXX(userInfo);
        System.out.println(userInfo);
    }*/

    /**
     * 添加方法注释.
     *
     * @return 1
     */
    public static String getFormatDate(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(date));
            System.out.println(parse.toString());
            //parse将指定的字符串转换为date类型
            //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-01 00:00:00");
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     *
     * 添加方法注释.
     *
     * @param source
     *            source
     * @param target
     *            target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 设置类中为null的属性的默认值
     * @param 、、obj 对应的类
     * @param 。。是否设置obj的父类的属性
     */
    public static void notNull(Object obj,boolean s) {
       if (s) {
           try {
               if (null == obj) {
                   return;
               }
               //getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
               //getDeclaredFields获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
               Field[] fields = obj.getClass().getDeclaredFields();
               Class cla = obj.getClass();
               for (Field field : fields) {
                   field.setAccessible(true);
                   if (field.get(obj) == null) {
                       //设置属性的默认值
                       setFieldValue(obj, cla, field);
                   }
               }
               //设置父类中的属性方法默认值
               Class superclass = cla.getSuperclass();
               System.out.println("superclass：" + superclass);
               System.out.println("superclass.getName()：" + superclass.getName());
               System.out.println("superclass.getDeclaredFields()：" + superclass.getDeclaredFields());
               Field[] declaredFields = superclass.getDeclaredFields();
               for (Field field : declaredFields) {
                   field.setAccessible(true);
                   if (null == field.get(obj)) {
                       setFieldValue(obj, cla, field);
                   }
                   field.setAccessible(false);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }else {
           notNull(obj);
       }
    }

    /**
     *  设置属性的默认值
     *
     * @param obj
     *            1
     * @param cla
     *            1
     * @param field
     *            1
     * @throws Exception
     *             1
     */
    private static void setFieldValue(Object obj, Class cla, Field field) throws Exception {
        // NoSuchMethodException, IllegalAccessException, InvocationTargetException
        Class type = field.getType();
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method;
        if (String.class.equals(type)) {
            method = cla.getMethod(methodName, String.class);
            method.invoke(obj, "");
        } else if (char.class.equals(type)) {
            method = cla.getMethod(methodName, char.class);
            method.invoke(obj, ' ');
        } else if (Character.class.equals(type)) {
            method = cla.getMethod(methodName, Character.class);
            method.invoke(obj, ' ');
        } else if (boolean.class.equals(type)) {
            method = cla.getMethod(methodName, boolean.class);
            method.invoke(obj, true);
        } else if (Boolean.class.equals(type)) {
            method = cla.getMethod(methodName, Boolean.class);
            method.invoke(obj, true);
        } else if (byte.class.equals(type)) {
            method = cla.getMethod(methodName, byte.class);
            method.invoke(obj, (byte) 0);
        } else if (Byte.class.equals(type)) {
            method = cla.getMethod(methodName, Byte.class);
            method.invoke(obj, (byte) 0);
        } else if (short.class.equals(type)) {
            method = cla.getMethod(methodName, short.class);
            method.invoke(obj, (short) 0);
        } else if (Short.class.equals(type)) {
            method = cla.getMethod(methodName, Short.class);
            method.invoke(obj, (short) 0);
        } else if (int.class.equals(type)) {
            method = cla.getMethod(methodName, int.class);
            method.invoke(obj, 0);
        } else if (Integer.class.equals(type)) {
            method = cla.getMethod(methodName, Integer.class);
            method.invoke(obj, 0);
        } else if (long.class.equals(type)) {
            method = cla.getMethod(methodName, long.class);
            method.invoke(obj, 0);
        } else if (Long.class.equals(type)) {
            method = cla.getMethod(methodName, Long.class);
            method.invoke(obj, 0L); // 修改了L，以前是小写
        } else if (float.class.equals(type)) {
            method = cla.getMethod(methodName, float.class);
            method.invoke(obj, 0.0f);
        } else if (Float.class.equals(type)) {
            method = cla.getMethod(methodName, Float.class);
            method.invoke(obj, 0.0f);
        } else if (double.class.equals(type)) {
            method = cla.getMethod(methodName, double.class);
            method.invoke(obj, 0.0d);
        } else if (Double.class.equals(type)) {
            method = cla.getMethod(methodName, Double.class);
            method.invoke(obj, 0.0d);
        } else if (Date.class.equals(type)) {
            method = cla.getMethod(methodName, Date.class);
            method.invoke(obj, new Date());
        } else if (BigDecimal.class.equals(type)) {
            method = cla.getMethod(methodName, BigDecimal.class);
            method.invoke(obj, new BigDecimal(0.0D));
        } else if (Timestamp.class.equals(type)) {
            method = cla.getMethod(methodName, Timestamp.class);
            method.invoke(obj, new Timestamp(System.currentTimeMillis()));
        }
    }

    /**
     * 对象属性值为null处理.
     *
     * @param obj
     *            对象
     */
    public static void notNull(Object obj) {
        try {
            if (null == obj) {
                return;
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            Class cla = obj.getClass();
            for (Field field : fields) {
                field.setAccessible(true);
                if (null == field.get(obj)) {
                    setFieldValue(obj, cla, field);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * TODO 检测对象字段是否有null.
     *
     * @param obj
     *            目标对象
     * @param other
     *            排除的字段
     * @return true/false
     */
    public static Boolean nullCheck(Object obj, List<String> other) {
        try {
            if (null == obj) {
                return true;
            }

            Field[] fields = obj.getClass().getDeclaredFields();
            Class cla = obj.getClass();
            for (Field field : fields) {

                field.setAccessible(true);
                if (null != other && other.contains(field.getName())) {
                    continue;
                }

                if (null == field.get(obj)) {
                    return true;
                }
                field.setAccessible(false);
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * copy级别-原始数据不为null.
     */
    public static final int COPY_SRC_NULL = 1;

    /**
     * copy级别-原始数据不为null或empty.
     */
    public static final int COPY_SRC_NULLOREMPTY = 2;

    /**
     * copy级别-目标数据不为null.
     */
    public static final int COPY_DEST_NULL = 3;

    /**
     * copy级别-目标数据不为null或empty.
     */
    public static final int COPY_DEST_NULLOREMPTY = 4;

    /**
     * fullDeepCopy：source to dest(dest完全完全深复制为source).
     *
     * @param source
     *            源实体类
     * @param dest
     *            目标实体类
     */
    public static void copy(Object source, Object dest) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (int i = 0, j = fields.length; i < j; i++) {
            String propertyName = fields[i].getName();
            Object propertyValue = getProperty(source, propertyName);
            setProperty(dest, propertyName, propertyValue);
        }
    }

    /**
     * 按需复制source里的属性到dest.
     *
     * @param source
     *            源实体类
     * @param dest
     *            目标实体类
     * @param coverLevel
     *            覆盖级别： 1:source_field != null; 2:source_field != null (&& !"".equals(source_field)); 3:dest_field==null; 4:dest_field==null(|| "".equals(dest_field)); others:fullDeepCopy。
     */
    public static void copy(Object source, Object dest, int coverLevel) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (int i = 0, j = fields.length; i < j; i++) {
            String propertyName = fields[i].getName();
            Object propertyValue = getProperty(source, propertyName);
            switch (coverLevel) {
                case 1:
                    if (getProperty(source, propertyName) != null) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case 2:
                    if (!isNullOrEmpty(getProperty(source, propertyName))) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case 3:
                    if (null == getProperty(dest, propertyName)) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case 4:
                    if (isNullOrEmpty(getProperty(dest, propertyName))) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                default:
                    setProperty(dest, propertyName, propertyValue);
                    break;
            }
        }
    }

    /**
     * 根据属性名取值.
     *
     * @param bean
     *            实体
     * @param propertyName
     *            属性名
     * @return value
     */
    public static Object getProperty(Object bean, String propertyName) {
        Class clazz = bean.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(propertyName);
            Method method = clazz.getDeclaredMethod(getGetterName(field.getName()), new Class[]{});
            return method.invoke(bean, new Object[]{});
        } catch (Exception e) {
            if ("boolean".equals(field.getType()) || "java.lang.Boolean".equals(field.getType().getName())) { // field.getType()即class java.lang.Boolean判断equals失败
                try {
                    Method method = clazz.getDeclaredMethod(getIsName(field.getName()), new Class[]{});
                    return method.invoke(bean, new Object[]{});
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 给指定属性赋值.
     *
     * @param bean
     *            实体
     * @param propertyName
     *            属性名
     * @param value
     *            值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        Class clazz = bean.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(propertyName);
            Method method = clazz.getDeclaredMethod(getSetterName(field.getName()), new Class[]{field.getType()});
            method.invoke(bean, new Object[]{value});
        } catch (Exception e) {
            if (null != field && ("boolean".equals(field.getType()) || "java.lang.Boolean".equals(field.getType().getName()))) {
                try {
                    Method method = clazz.getDeclaredMethod(getIsName(field.getName()), new Class[]{field.getType()});
                    method.invoke(bean, new Object[]{value});
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据属性名得到get方法.
     *
     * @param propertyName
     *            属性名
     * @return getName
     */
    private static String getGetterName(String propertyName) {
        return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * 根据属性名得到is方法(boolean|Bollean型).
     *
     * @param propertyName
     *            属性名
     * @return getName
     */
    private static String getIsName(String propertyName) {
        return "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * 根据属性名得到set方法.
     *
     * @param propertyName
     *            属性名
     * @return setName
     */
    private static String getSetterName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * 判断src是否为null(若src为String,判断是否为"").
     *
     * @param src
     *            src
     * @return boolean
     */
    private static boolean isNullOrEmpty(Object src) {
        if (null == src) {
            return true;
        }
        if (src instanceof String) {
            return "".equals(((String) src).trim());
        }
        return false;
    }

    /**
     * 拼接属性（尽量使用包装类，基本类型会有默认值）.
     *
     * @param obj
     *            obj
     * @return 拼接结果
     * @throws Exception
     *             Exception
     */
    public static String joinProperty(Object obj) throws Exception {
        StringBuffer value = new StringBuffer(100);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String upper = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m = null;
            try {
                m = obj.getClass().getMethod("get" + upper);
            } catch (NoSuchMethodException e) {
                m = obj.getClass().getMethod("is" + upper);
            }
            if (null != m.invoke(obj)) {
                value.append("&" + name + "=");
                value.append(m.invoke(obj));
            }
        }
        return value.toString();
    }
}
