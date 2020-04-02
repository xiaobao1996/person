package cn.hy.infoReport.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IrMonitorHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public IrMonitorHistoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andMonitorDateIsNull() {
            addCriterion("monitor_date is null");
            return (Criteria) this;
        }

        public Criteria andMonitorDateIsNotNull() {
            addCriterion("monitor_date is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorDateEqualTo(Date value) {
            addCriterionForJDBCDate("monitor_date =", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("monitor_date <>", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateGreaterThan(Date value) {
            addCriterionForJDBCDate("monitor_date >", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("monitor_date >=", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateLessThan(Date value) {
            addCriterionForJDBCDate("monitor_date <", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("monitor_date <=", value, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateIn(List<Date> values) {
            addCriterionForJDBCDate("monitor_date in", values, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("monitor_date not in", values, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("monitor_date between", value1, value2, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("monitor_date not between", value1, value2, "monitorDate");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeIsNull() {
            addCriterion("monitor_datetime is null");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeIsNotNull() {
            addCriterion("monitor_datetime is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeEqualTo(Date value) {
            addCriterion("monitor_datetime =", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeNotEqualTo(Date value) {
            addCriterion("monitor_datetime <>", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeGreaterThan(Date value) {
            addCriterion("monitor_datetime >", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("monitor_datetime >=", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeLessThan(Date value) {
            addCriterion("monitor_datetime <", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("monitor_datetime <=", value, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeIn(List<Date> values) {
            addCriterion("monitor_datetime in", values, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeNotIn(List<Date> values) {
            addCriterion("monitor_datetime not in", values, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeBetween(Date value1, Date value2) {
            addCriterion("monitor_datetime between", value1, value2, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("monitor_datetime not between", value1, value2, "monitorDatetime");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdIsNull() {
            addCriterion("monitor_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdIsNotNull() {
            addCriterion("monitor_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdEqualTo(String value) {
            addCriterion("monitor_user_id =", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdNotEqualTo(String value) {
            addCriterion("monitor_user_id <>", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdGreaterThan(String value) {
            addCriterion("monitor_user_id >", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_user_id >=", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdLessThan(String value) {
            addCriterion("monitor_user_id <", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdLessThanOrEqualTo(String value) {
            addCriterion("monitor_user_id <=", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdLike(String value) {
            addCriterion("monitor_user_id like", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdNotLike(String value) {
            addCriterion("monitor_user_id not like", value, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdIn(List<String> values) {
            addCriterion("monitor_user_id in", values, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdNotIn(List<String> values) {
            addCriterion("monitor_user_id not in", values, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdBetween(String value1, String value2) {
            addCriterion("monitor_user_id between", value1, value2, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserIdNotBetween(String value1, String value2) {
            addCriterion("monitor_user_id not between", value1, value2, "monitorUserId");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameIsNull() {
            addCriterion("monitor_user_name is null");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameIsNotNull() {
            addCriterion("monitor_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameEqualTo(String value) {
            addCriterion("monitor_user_name =", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameNotEqualTo(String value) {
            addCriterion("monitor_user_name <>", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameGreaterThan(String value) {
            addCriterion("monitor_user_name >", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_user_name >=", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameLessThan(String value) {
            addCriterion("monitor_user_name <", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameLessThanOrEqualTo(String value) {
            addCriterion("monitor_user_name <=", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameLike(String value) {
            addCriterion("monitor_user_name like", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameNotLike(String value) {
            addCriterion("monitor_user_name not like", value, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameIn(List<String> values) {
            addCriterion("monitor_user_name in", values, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameNotIn(List<String> values) {
            addCriterion("monitor_user_name not in", values, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameBetween(String value1, String value2) {
            addCriterion("monitor_user_name between", value1, value2, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorUserNameNotBetween(String value1, String value2) {
            addCriterion("monitor_user_name not between", value1, value2, "monitorUserName");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceIsNull() {
            addCriterion("monitor_device is null");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceIsNotNull() {
            addCriterion("monitor_device is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceEqualTo(String value) {
            addCriterion("monitor_device =", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceNotEqualTo(String value) {
            addCriterion("monitor_device <>", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceGreaterThan(String value) {
            addCriterion("monitor_device >", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_device >=", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceLessThan(String value) {
            addCriterion("monitor_device <", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceLessThanOrEqualTo(String value) {
            addCriterion("monitor_device <=", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceLike(String value) {
            addCriterion("monitor_device like", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceNotLike(String value) {
            addCriterion("monitor_device not like", value, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceIn(List<String> values) {
            addCriterion("monitor_device in", values, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceNotIn(List<String> values) {
            addCriterion("monitor_device not in", values, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceBetween(String value1, String value2) {
            addCriterion("monitor_device between", value1, value2, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorDeviceNotBetween(String value1, String value2) {
            addCriterion("monitor_device not between", value1, value2, "monitorDevice");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceIsNull() {
            addCriterion("monitor_place is null");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceIsNotNull() {
            addCriterion("monitor_place is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceEqualTo(String value) {
            addCriterion("monitor_place =", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceNotEqualTo(String value) {
            addCriterion("monitor_place <>", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceGreaterThan(String value) {
            addCriterion("monitor_place >", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_place >=", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceLessThan(String value) {
            addCriterion("monitor_place <", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceLessThanOrEqualTo(String value) {
            addCriterion("monitor_place <=", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceLike(String value) {
            addCriterion("monitor_place like", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceNotLike(String value) {
            addCriterion("monitor_place not like", value, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceIn(List<String> values) {
            addCriterion("monitor_place in", values, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceNotIn(List<String> values) {
            addCriterion("monitor_place not in", values, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceBetween(String value1, String value2) {
            addCriterion("monitor_place between", value1, value2, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andMonitorPlaceNotBetween(String value1, String value2) {
            addCriterion("monitor_place not between", value1, value2, "monitorPlace");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNull() {
            addCriterion("temperature is null");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNotNull() {
            addCriterion("temperature is not null");
            return (Criteria) this;
        }

        public Criteria andTemperatureEqualTo(BigDecimal value) {
            addCriterion("temperature =", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotEqualTo(BigDecimal value) {
            addCriterion("temperature <>", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThan(BigDecimal value) {
            addCriterion("temperature >", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("temperature >=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThan(BigDecimal value) {
            addCriterion("temperature <", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThanOrEqualTo(BigDecimal value) {
            addCriterion("temperature <=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureIn(List<BigDecimal> values) {
            addCriterion("temperature in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotIn(List<BigDecimal> values) {
            addCriterion("temperature not in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("temperature between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("temperature not between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Byte value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Byte value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Byte value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Byte value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Byte value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Byte> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Byte> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Byte value1, Byte value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(String value) {
            addCriterion("school_id =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(String value) {
            addCriterion("school_id <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(String value) {
            addCriterion("school_id >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(String value) {
            addCriterion("school_id >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(String value) {
            addCriterion("school_id <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(String value) {
            addCriterion("school_id <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLike(String value) {
            addCriterion("school_id like", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotLike(String value) {
            addCriterion("school_id not like", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<String> values) {
            addCriterion("school_id in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<String> values) {
            addCriterion("school_id not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(String value1, String value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(String value1, String value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}