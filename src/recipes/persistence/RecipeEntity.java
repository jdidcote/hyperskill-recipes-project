package recipes.persistence;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> directions;

    @ManyToOne
    @JoinColumn
    private UserEntity user;
}
