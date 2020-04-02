package cn.hy.infoReport.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IrHealthMonitorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public IrHealthMonitorExample() {
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

        public Criteria andAmTemperatureIsNull() {
            addCriterion("am_temperature is null");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureIsNotNull() {
            addCriterion("am_temperature is not null");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureEqualTo(BigDecimal value) {
            addCriterion("am_temperature =", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureNotEqualTo(BigDecimal value) {
            addCriterion("am_temperature <>", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureGreaterThan(BigDecimal value) {
            addCriterion("am_temperature >", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("am_temperature >=", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureLessThan(BigDecimal value) {
            addCriterion("am_temperature <", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureLessThanOrEqualTo(BigDecimal value) {
            addCriterion("am_temperature <=", value, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureIn(List<BigDecimal> values) {
            addCriterion("am_temperature in", values, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureNotIn(List<BigDecimal> values) {
            addCriterion("am_temperature not in", values, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("am_temperature between", value1, value2, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmTemperatureNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("am_temperature not between", value1, value2, "amTemperature");
            return (Criteria) this;
        }

        public Criteria andAmRecheckIsNull() {
            addCriterion("am_recheck is null");
            return (Criteria) this;
        }

        public Criteria andAmRecheckIsNotNull() {
            addCriterion("am_recheck is not null");
            return (Criteria) this;
        }

        public Criteria andAmRecheckEqualTo(Byte value) {
            addCriterion("am_recheck =", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckNotEqualTo(Byte value) {
            addCriterion("am_recheck <>", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckGreaterThan(Byte value) {
            addCriterion("am_recheck >", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckGreaterThanOrEqualTo(Byte value) {
            addCriterion("am_recheck >=", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckLessThan(Byte value) {
            addCriterion("am_recheck <", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckLessThanOrEqualTo(Byte value) {
            addCriterion("am_recheck <=", value, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckIn(List<Byte> values) {
            addCriterion("am_recheck in", values, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckNotIn(List<Byte> values) {
            addCriterion("am_recheck not in", values, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckBetween(Byte value1, Byte value2) {
            addCriterion("am_recheck between", value1, value2, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmRecheckNotBetween(Byte value1, Byte value2) {
            addCriterion("am_recheck not between", value1, value2, "amRecheck");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeIsNull() {
            addCriterion("am_check_time is null");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeIsNotNull() {
            addCriterion("am_check_time is not null");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeEqualTo(Date value) {
            addCriterion("am_check_time =", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeNotEqualTo(Date value) {
            addCriterion("am_check_time <>", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeGreaterThan(Date value) {
            addCriterion("am_check_time >", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("am_check_time >=", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeLessThan(Date value) {
            addCriterion("am_check_time <", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("am_check_time <=", value, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeIn(List<Date> values) {
            addCriterion("am_check_time in", values, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeNotIn(List<Date> values) {
            addCriterion("am_check_time not in", values, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeBetween(Date value1, Date value2) {
            addCriterion("am_check_time between", value1, value2, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("am_check_time not between", value1, value2, "amCheckTime");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckIsNull() {
            addCriterion("am_human_check is null");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckIsNotNull() {
            addCriterion("am_human_check is not null");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckEqualTo(Byte value) {
            addCriterion("am_human_check =", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckNotEqualTo(Byte value) {
            addCriterion("am_human_check <>", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckGreaterThan(Byte value) {
            addCriterion("am_human_check >", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckGreaterThanOrEqualTo(Byte value) {
            addCriterion("am_human_check >=", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckLessThan(Byte value) {
            addCriterion("am_human_check <", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckLessThanOrEqualTo(Byte value) {
            addCriterion("am_human_check <=", value, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckIn(List<Byte> values) {
            addCriterion("am_human_check in", values, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckNotIn(List<Byte> values) {
            addCriterion("am_human_check not in", values, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckBetween(Byte value1, Byte value2) {
            addCriterion("am_human_check between", value1, value2, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andAmHumanCheckNotBetween(Byte value1, Byte value2) {
            addCriterion("am_human_check not between", value1, value2, "amHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureIsNull() {
            addCriterion("pm_temperature is null");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureIsNotNull() {
            addCriterion("pm_temperature is not null");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureEqualTo(BigDecimal value) {
            addCriterion("pm_temperature =", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureNotEqualTo(BigDecimal value) {
            addCriterion("pm_temperature <>", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureGreaterThan(BigDecimal value) {
            addCriterion("pm_temperature >", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pm_temperature >=", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureLessThan(BigDecimal value) {
            addCriterion("pm_temperature <", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pm_temperature <=", value, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureIn(List<BigDecimal> values) {
            addCriterion("pm_temperature in", values, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureNotIn(List<BigDecimal> values) {
            addCriterion("pm_temperature not in", values, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pm_temperature between", value1, value2, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmTemperatureNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pm_temperature not between", value1, value2, "pmTemperature");
            return (Criteria) this;
        }

        public Criteria andPmRecheckIsNull() {
            addCriterion("pm_recheck is null");
            return (Criteria) this;
        }

        public Criteria andPmRecheckIsNotNull() {
            addCriterion("pm_recheck is not null");
            return (Criteria) this;
        }

        public Criteria andPmRecheckEqualTo(Byte value) {
            addCriterion("pm_recheck =", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckNotEqualTo(Byte value) {
            addCriterion("pm_recheck <>", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckGreaterThan(Byte value) {
            addCriterion("pm_recheck >", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckGreaterThanOrEqualTo(Byte value) {
            addCriterion("pm_recheck >=", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckLessThan(Byte value) {
            addCriterion("pm_recheck <", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckLessThanOrEqualTo(Byte value) {
            addCriterion("pm_recheck <=", value, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckIn(List<Byte> values) {
            addCriterion("pm_recheck in", values, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckNotIn(List<Byte> values) {
            addCriterion("pm_recheck not in", values, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckBetween(Byte value1, Byte value2) {
            addCriterion("pm_recheck between", value1, value2, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmRecheckNotBetween(Byte value1, Byte value2) {
            addCriterion("pm_recheck not between", value1, value2, "pmRecheck");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeIsNull() {
            addCriterion("pm_check_time is null");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeIsNotNull() {
            addCriterion("pm_check_time is not null");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeEqualTo(Date value) {
            addCriterion("pm_check_time =", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeNotEqualTo(Date value) {
            addCriterion("pm_check_time <>", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeGreaterThan(Date value) {
            addCriterion("pm_check_time >", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pm_check_time >=", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeLessThan(Date value) {
            addCriterion("pm_check_time <", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("pm_check_time <=", value, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeIn(List<Date> values) {
            addCriterion("pm_check_time in", values, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeNotIn(List<Date> values) {
            addCriterion("pm_check_time not in", values, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeBetween(Date value1, Date value2) {
            addCriterion("pm_check_time between", value1, value2, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("pm_check_time not between", value1, value2, "pmCheckTime");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckIsNull() {
            addCriterion("pm_human_check is null");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckIsNotNull() {
            addCriterion("pm_human_check is not null");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckEqualTo(Byte value) {
            addCriterion("pm_human_check =", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckNotEqualTo(Byte value) {
            addCriterion("pm_human_check <>", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckGreaterThan(Byte value) {
            addCriterion("pm_human_check >", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckGreaterThanOrEqualTo(Byte value) {
            addCriterion("pm_human_check >=", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckLessThan(Byte value) {
            addCriterion("pm_human_check <", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckLessThanOrEqualTo(Byte value) {
            addCriterion("pm_human_check <=", value, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckIn(List<Byte> values) {
            addCriterion("pm_human_check in", values, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckNotIn(List<Byte> values) {
            addCriterion("pm_human_check not in", values, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckBetween(Byte value1, Byte value2) {
            addCriterion("pm_human_check between", value1, value2, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andPmHumanCheckNotBetween(Byte value1, Byte value2) {
            addCriterion("pm_human_check not between", value1, value2, "pmHumanCheck");
            return (Criteria) this;
        }

        public Criteria andHealthStatusIsNull() {
            addCriterion("health_status is null");
            return (Criteria) this;
        }

        public Criteria andHealthStatusIsNotNull() {
            addCriterion("health_status is not null");
            return (Criteria) this;
        }

        public Criteria andHealthStatusEqualTo(Byte value) {
            addCriterion("health_status =", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusNotEqualTo(Byte value) {
            addCriterion("health_status <>", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusGreaterThan(Byte value) {
            addCriterion("health_status >", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("health_status >=", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusLessThan(Byte value) {
            addCriterion("health_status <", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusLessThanOrEqualTo(Byte value) {
            addCriterion("health_status <=", value, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusIn(List<Byte> values) {
            addCriterion("health_status in", values, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusNotIn(List<Byte> values) {
            addCriterion("health_status not in", values, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusBetween(Byte value1, Byte value2) {
            addCriterion("health_status between", value1, value2, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andHealthStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("health_status not between", value1, value2, "healthStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Byte value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Byte value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Byte value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Byte value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Byte> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Byte> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
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

        public Criteria andUpdateAtIsNull() {
            addCriterion("update_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNotNull() {
            addCriterion("update_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtEqualTo(Date value) {
            addCriterion("update_at =", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotEqualTo(Date value) {
            addCriterion("update_at <>", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThan(Date value) {
            addCriterion("update_at >", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("update_at >=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThan(Date value) {
            addCriterion("update_at <", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThanOrEqualTo(Date value) {
            addCriterion("update_at <=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIn(List<Date> values) {
            addCriterion("update_at in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotIn(List<Date> values) {
            addCriterion("update_at not in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtBetween(Date value1, Date value2) {
            addCriterion("update_at between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotBetween(Date value1, Date value2) {
            addCriterion("update_at not between", value1, value2, "updateAt");
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