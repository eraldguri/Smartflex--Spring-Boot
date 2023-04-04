package com.smsolutions.smartflex.entity;

import com.smsolutions.smartflex.base.BaseEntity;
import com.smsolutions.smartflex.utils.constants.Columns;
import com.smsolutions.smartflex.utils.constants.Tables;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Tables.TABLE_USER)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Columns.Ids.USER_ID)
    private Long id;

    @Column(name = Columns.USERNAME)
    private String username;

    @Column(name = Columns.PASSWORD)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = Tables.TABLE_USER_AND_ROLE,
            joinColumns = @JoinColumn(name = Columns.Ids.USER_ID, referencedColumnName = Columns.Ids.USER_ID),
            inverseJoinColumns = @JoinColumn(name = Columns.Ids.ROLE_ID, referencedColumnName = Columns.Ids.ROLE_ID)
    )
    private Set<Role> roles = new HashSet<>();
}
