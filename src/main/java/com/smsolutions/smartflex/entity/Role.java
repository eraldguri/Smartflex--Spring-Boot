package com.smsolutions.smartflex.entity;

import com.smsolutions.smartflex.base.BaseEntity;
import com.smsolutions.smartflex.utils.constants.Columns;
import com.smsolutions.smartflex.utils.constants.Tables;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Tables.TABLE_ROLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Columns.Ids.ROLE_ID)
    private Long id;

    @Column(name = Columns.ROLE_NAME)
    private String name;

}
