package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminAllEventsDTO {

    private Long id;
    private String title;
    private Integer minAge;
    private Integer maxAge;
    private Integer maxNumberOfPeople;
    private String status;
    private Integer signedUp;
    private Integer waitingList;
}
