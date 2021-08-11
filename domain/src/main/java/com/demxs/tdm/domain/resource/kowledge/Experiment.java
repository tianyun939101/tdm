package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Experiment extends DataEntity<Experiment> {
        public static final String 编号_TBD = "编号TBD";
        private static final long serialVersionUID = 1L;
        //类型
        private String type;
        //服务
        private String service;
        //编号
        private String code;
        //名称
        private String name;
        //级别
        private String level;
        //部门
        private String org;
        //编制计划
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy/MM/dd")
        private Date plan;

        /**
         *portal 门户/表格进入
         * @return
         */
        private String portal;

        public String getPortal() {
                return portal;
        }

        public void setPortal(String portal) {
                this.portal = portal;
        }

        @ExcelField(title = "类型",sort = 0,type = 1)
        public String getType() {
                return type;
        }
        @ExcelField(title = "类型",sort = 0,type = 2)
        public void setType(String type) {
                this.type = type;
        }
        @ExcelField(title = "",sort = 1,type = 1)
        public String getService() {
                return service;
        }
        @ExcelField(title = "",sort = 1,type = 2)
        public void setService(String service) {
                this.service = service;
        }
        @ExcelField(title = "编号TBD",sort = 2,type = 1)
        public String getCode() {
                return code;
        }
        @ExcelField(title = 编号_TBD,sort = 2,type = 2)
        public void setCode(String code) {
                this.code = code;
        }
        @ExcelField(title = "名称",sort = 3,type = 1)
        public String getName() {
                return name;
        }
        @ExcelField(title = "名称",sort = 3,type = 2)
        public void setName(String name) {
                this.name = name;
        }
        @ExcelField(title = "级别",sort = 4,type = 1)
        public String getLevel() {
                return level;
        }
        @ExcelField(title = "级别",sort = 4,type = 2)
        public void setLevel(String level) {
                this.level = level;
        }
        @ExcelField(title = "部门",sort = 5,type = 1)
        public String getOrg() {
                return org;
        }
        @ExcelField(title = "部门",sort = 5,type = 2)
        public void setOrg(String org) {
                this.org = org;
        }
        @ExcelField(title = "计划",sort = 6,type = 1)
        public Date getPlan() {
                return plan;
        }
        @ExcelField(title = "计划",sort = 6,type = 2)
        public void setPlan(Date plan) {
                this.plan = plan;
        }






}
