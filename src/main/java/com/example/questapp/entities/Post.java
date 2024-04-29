package com.example.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    Long id;

    //User objesini db'den hemen çekme.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    //ColumnDefinition MySQL'in text'i string olarak algilanmasi icin yazildi. aksi takdirde nvarchar olarak algiliyor.
    @Lob
    @Column(columnDefinition = "text")
    String text;
}
