package com.demxs.tdm.domain.business.instrument;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;

public class DzToolManagement extends DataEntity<DzToolManagement> {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String id;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    private String current;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.category_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String categoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.code
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.name
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.model
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String model;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.number
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.position_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String positionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.manage_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String manageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.departments
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String departments;

    private String subCenter;

    public Office getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(Office labInfo) {
        this.labInfo = labInfo;
    }

    private Office labInfo;

    private DzCategory category;

    public DzStorageLocation getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(DzStorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    private DzStorageLocation storageLocation;

    public DzCategory getCategory() {
        return category;
    }

    public void setCategory(DzCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DZ_TOOL_MANAGEMENT.state
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table DZ_TOOL_MANAGEMENT
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.id
     *
     * @return the value of DZ_TOOL_MANAGEMENT.id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.id
     *
     * @param id the value for DZ_TOOL_MANAGEMENT.id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.category_id
     *
     * @return the value of DZ_TOOL_MANAGEMENT.category_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.category_id
     *
     * @param categoryId the value for DZ_TOOL_MANAGEMENT.category_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.code
     *
     * @return the value of DZ_TOOL_MANAGEMENT.code
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.code
     *
     * @param code the value for DZ_TOOL_MANAGEMENT.code
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.name
     *
     * @return the value of DZ_TOOL_MANAGEMENT.name
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.name
     *
     * @param name the value for DZ_TOOL_MANAGEMENT.name
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.model
     *
     * @return the value of DZ_TOOL_MANAGEMENT.model
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getModel() {
        return model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.model
     *
     * @param model the value for DZ_TOOL_MANAGEMENT.model
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.number
     *
     * @return the value of DZ_TOOL_MANAGEMENT.number
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.number
     *
     * @param number the value for DZ_TOOL_MANAGEMENT.number
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.position_id
     *
     * @return the value of DZ_TOOL_MANAGEMENT.position_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getPositionId() {
        return positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.position_id
     *
     * @param positionId the value for DZ_TOOL_MANAGEMENT.position_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.manage_id
     *
     * @return the value of DZ_TOOL_MANAGEMENT.manage_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getManageId() {
        return manageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.manage_id
     *
     * @param manageId the value for DZ_TOOL_MANAGEMENT.manage_id
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setManageId(String manageId) {
        this.manageId = manageId == null ? null : manageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.departments
     *
     * @return the value of DZ_TOOL_MANAGEMENT.departments
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getDepartments() {
        return departments;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.departments
     *
     * @param departments the value for DZ_TOOL_MANAGEMENT.departments
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setDepartments(String departments) {
        this.departments = departments == null ? null : departments.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DZ_TOOL_MANAGEMENT.state
     *
     * @return the value of DZ_TOOL_MANAGEMENT.state
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DZ_TOOL_MANAGEMENT.state
     *
     * @param state the value for DZ_TOOL_MANAGEMENT.state
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }


    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }
}