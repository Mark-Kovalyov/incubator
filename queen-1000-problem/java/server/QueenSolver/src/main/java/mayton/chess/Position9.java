package mayton.chess;

import javax.persistence.*;

@Entity
@Table(name = "POSITION_9")
public class Position9 {

    @Id
    @SequenceGenerator(name = "POS_GENERATOR", sequenceName = "POSITION_9_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POS_GENERATOR")
    private Long id;

    private String positionCode;

    public Position9(){
    }

    public Position9(String positionCode){
        this.positionCode = positionCode;
    }

    public Position9(Long id, String positionCode) {
        this.id = id;
        this.positionCode = positionCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Override
    public String toString() {
        return "Position9{" +
                "id=" + id +
                ", positionCode='" + positionCode + '\'' +
                '}';
    }
}
