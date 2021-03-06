package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel<Long> {

    private String name;
}
