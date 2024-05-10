package com.example.questapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //User objesini db'den hemen çekme.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) //bir user silindiğinde tüm postları silinir.
    User user;

    String title;
    //ColumnDefinition MySQL'in text'i string olarak algilanmasi icin yazildi. aksi takdirde nvarchar olarak algiliyor.
    @Lob
    @Column(columnDefinition = "text")
    String text;
}
