package cn.hy.infoReport.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IrReportConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public IrReportConfigExample() {
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

        public Criteria andStudentReportIdentifyIsNull() {
            addCriterion("student_report_identify is null");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyIsNotNull() {
            addCriterion("student_report_identify is not null");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyEqualTo(String value) {
            addCriterion("student_report_identify =", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyNotEqualTo(String value) {
            addCriterion("student_report_identify <>", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyGreaterThan(String value) {
            addCriterion("student_report_identify >", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyGreaterThanOrEqualTo(String value) {
            addCriterion("student_report_identify >=", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyLessThan(String value) {
            addCriterion("student_report_identify <", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyLessThanOrEqualTo(String value) {
            addCriterion("student_report_identify <=", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyLike(String value) {
            addCriterion("student_report_identify like", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyNotLike(String value) {
            addCriterion("student_report_identify not like", value, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyIn(List<String> values) {
            addCriterion("student_report_identify in", values, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyNotIn(List<String> values) {
            addCriterion("student_report_identify not in", values, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyBetween(String value1, String value2) {
            addCriterion("student_report_identify between", value1, value2, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportIdentifyNotBetween(String value1, String value2) {
            addCriterion("student_report_identify not between", value1, value2, "studentReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsIsNull() {
            addCriterion("student_report_user_ids is null");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsIsNotNull() {
            addCriterion("student_report_user_ids is not null");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsEqualTo(String value) {
            addCriterion("student_report_user_ids =", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsNotEqualTo(String value) {
            addCriterion("student_report_user_ids <>", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsGreaterThan(String value) {
            addCriterion("student_report_user_ids >", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsGreaterThanOrEqualTo(String value) {
            addCriterion("student_report_user_ids >=", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsLessThan(String value) {
            addCriterion("student_report_user_ids <", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsLessThanOrEqualTo(String value) {
            addCriterion("student_report_user_ids <=", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsLike(String value) {
            addCriterion("student_report_user_ids like", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsNotLike(String value) {
            addCriterion("student_report_user_ids not like", value, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsIn(List<String> values) {
            addCriterion("student_report_user_ids in", values, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsNotIn(List<String> values) {
            addCriterion("student_report_user_ids not in", values, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsBetween(String value1, String value2) {
            addCriterion("student_report_user_ids between", value1, value2, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStudentReportUserIdsNotBetween(String value1, String value2) {
            addCriterion("student_report_user_ids not between", value1, value2, "studentReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyIsNull() {
            addCriterion("staff_report_identify is null");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyIsNotNull() {
            addCriterion("staff_report_identify is not null");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyEqualTo(String value) {
            addCriterion("staff_report_identify =", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyNotEqualTo(String value) {
            addCriterion("staff_report_identify <>", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyGreaterThan(String value) {
            addCriterion("staff_report_identify >", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyGreaterThanOrEqualTo(String value) {
            addCriterion("staff_report_identify >=", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyLessThan(String value) {
            addCriterion("staff_report_identify <", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyLessThanOrEqualTo(String value) {
            addCriterion("staff_report_identify <=", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyLike(String value) {
            addCriterion("staff_report_identify like", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyNotLike(String value) {
            addCriterion("staff_report_identify not like", value, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyIn(List<String> values) {
            addCriterion("staff_report_identify in", values, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyNotIn(List<String> values) {
            addCriterion("staff_report_identify not in", values, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyBetween(String value1, String value2) {
            addCriterion("staff_report_identify between", value1, value2, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportIdentifyNotBetween(String value1, String value2) {
            addCriterion("staff_report_identify not between", value1, value2, "staffReportIdentify");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsIsNull() {
            addCriterion("staff_report_user_ids is null");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsIsNotNull() {
            addCriterion("staff_report_user_ids is not null");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsEqualTo(String value) {
            addCriterion("staff_report_user_ids =", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsNotEqualTo(String value) {
            addCriterion("staff_report_user_ids <>", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsGreaterThan(String value) {
            addCriterion("staff_report_user_ids >", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsGreaterThanOrEqualTo(String value) {
            addCriterion("staff_report_user_ids >=", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsLessThan(String value) {
            addCriterion("staff_report_user_ids <", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsLessThanOrEqualTo(String value) {
            addCriterion("staff_report_user_ids <=", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsLike(String value) {
            addCriterion("staff_report_user_ids like", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsNotLike(String value) {
            addCriterion("staff_report_user_ids not like", value, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsIn(List<String> values) {
            addCriterion("staff_report_user_ids in", values, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsNotIn(List<String> values) {
            addCriterion("staff_report_user_ids not in", values, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsBetween(String value1, String value2) {
            addCriterion("staff_report_user_ids between", value1, value2, "staffReportUserIds");
            return (Criteria) this;
        }

        public Criteria andStaffReportUserIdsNotBetween(String value1, String value2) {
            addCriterion("staff_report_user_ids not between", value1, value2, "staffReportUserIds");
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