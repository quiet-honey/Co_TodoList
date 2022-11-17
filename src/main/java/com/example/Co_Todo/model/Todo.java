package com.example.Co_Todo.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder // 빌더를 생성해줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성해줌
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성해줌
@Data // getter, setter, toString을 자동 생성해줌
@Entity // DB에서의 Entity로써 테이블의 스키마
//@Table(name = "Todo") // 클래스 이름 그대로 생성되므로 필요 없음
public class Todo {

    @Id //Primary Key로 설정
    @GeneratedValue(generator = "uuid") // 랜덤
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;
    private String title;
    private boolean done;
}
