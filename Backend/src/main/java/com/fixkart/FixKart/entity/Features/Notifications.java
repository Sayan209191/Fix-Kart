package com.fixkart.FixKart.entity.Features;


import com.fixkart.FixKart.entity.User.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mst_Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mst_user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_mst_user_id"))
    private Users user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime notificationTime = LocalDateTime.now();

}
