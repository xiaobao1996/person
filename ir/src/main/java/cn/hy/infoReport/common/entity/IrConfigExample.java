package cn.hy.infoReport.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IrConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public IrConfigExample() {
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

        public Criteria andTemperatureLowLimitIsNull() {
            addCriterion("temperature_low_limit is null");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitIsNotNull() {
            addCriterion("temperature_low_limit is not null");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitEqualTo(BigDecimal value) {
            addCriterion("temperature_low_limit =", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitNotEqualTo(BigDecimal value) {
            addCriterion("temperature_low_limit <>", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitGreaterThan(BigDecimal value) {
            addCriterion("temperature_low_limit >", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("temperature_low_limit >=", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitLessThan(BigDecimal value) {
            addCriterion("temperature_low_limit <", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("temperature_low_limit <=", value, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitIn(List<BigDecimal> values) {
            addCriterion("temperature_low_limit in", values, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitNotIn(List<BigDecimal> values) {
            addCriterion("temperature_low_limit not in", values, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("temperature_low_limit between", value1, value2, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andTemperatureLowLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("temperature_low_limit not between", value1, value2, "temperatureLowLimit");
            return (Criteria) this;
        }

        public Criteria andNoPassIsNull() {
            addCriterion("no_pass is null");
            return (Criteria) this;
        }

        public Criteria andNoPassIsNotNull() {
            addCriterion("no_pass is not null");
            return (Criteria) this;
        }

        public Criteria andNoPassEqualTo(Byte value) {
            addCriterion("no_pass =", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassNotEqualTo(Byte value) {
            addCriterion("no_pass <>", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassGreaterThan(Byte value) {
            addCriterion("no_pass >", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassGreaterThanOrEqualTo(Byte value) {
            addCriterion("no_pass >=", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassLessThan(Byte value) {
            addCriterion("no_pass <", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassLessThanOrEqualTo(Byte value) {
            addCriterion("no_pass <=", value, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassIn(List<Byte> values) {
            addCriterion("no_pass in", values, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassNotIn(List<Byte> values) {
            addCriterion("no_pass not in", values, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassBetween(Byte value1, Byte value2) {
            addCriterion("no_pass between", value1, value2, "noPass");
            return (Criteria) this;
        }

        public Criteria andNoPassNotBetween(Byte value1, Byte value2) {
            addCriterion("no_pass not between", value1, value2, "noPass");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmIsNull() {
            addCriterion("device_alarm is null");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmIsNotNull() {
            addCriterion("device_alarm is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmEqualTo(Byte value) {
            addCriterion("device_alarm =", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmNotEqualTo(Byte value) {
            addCriterion("device_alarm <>", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmGreaterThan(Byte value) {
            addCriterion("device_alarm >", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmGreaterThanOrEqualTo(Byte value) {
            addCriterion("device_alarm >=", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmLessThan(Byte value) {
            addCriterion("device_alarm <", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmLessThanOrEqualTo(Byte value) {
            addCriterion("device_alarm <=", value, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmIn(List<Byte> values) {
            addCriterion("device_alarm in", values, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmNotIn(List<Byte> values) {
            addCriterion("device_alarm not in", values, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmBetween(Byte value1, Byte value2) {
            addCriterion("device_alarm between", value1, value2, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andDeviceAlarmNotBetween(Byte value1, Byte value2) {
            addCriterion("device_alarm not between", value1, value2, "deviceAlarm");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberIsNull() {
            addCriterion("notify_continuous_number is null");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberIsNotNull() {
            addCriterion("notify_continuous_number is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberEqualTo(Integer value) {
            addCriterion("notify_continuous_number =", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberNotEqualTo(Integer value) {
            addCriterion("notify_continuous_number <>", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberGreaterThan(Integer value) {
            addCriterion("notify_continuous_number >", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("notify_continuous_number >=", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberLessThan(Integer value) {
            addCriterion("notify_continuous_number <", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberLessThanOrEqualTo(Integer value) {
            addCriterion("notify_continuous_number <=", value, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberIn(List<Integer> values) {
            addCriterion("notify_continuous_number in", values, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberNotIn(List<Integer> values) {
            addCriterion("notify_continuous_number not in", values, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberBetween(Integer value1, Integer value2) {
            addCriterion("notify_continuous_number between", value1, value2, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andNotifyContinuousNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("notify_continuous_number not between", value1, value2, "notifyContinuousNumber");
            return (Criteria) this;
        }

        public Criteria andModeIsNull() {
            addCriterion("mode is null");
            return (Criteria) this;
        }

        public Criteria andModeIsNotNull() {
            addCriterion("mode is not null");
            return (Criteria) this;
        }

        public Criteria andModeEqualTo(Byte value) {
            addCriterion("mode =", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotEqualTo(Byte value) {
            addCriterion("mode <>", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThan(Byte value) {
            addCriterion("mode >", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("mode >=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThan(Byte value) {
            addCriterion("mode <", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThanOrEqualTo(Byte value) {
            addCriterion("mode <=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeIn(List<Byte> values) {
            addCriterion("mode in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotIn(List<Byte> values) {
            addCriterion("mode not in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeBetween(Byte value1, Byte value2) {
            addCriterion("mode between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotBetween(Byte value1, Byte value2) {
            addCriterion("mode not between", value1, value2, "mode");
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