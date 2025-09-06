package mayton.chess;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionRepository extends CrudRepository<Position9, Long> {

    //@Query(value = "SELECT * FROM POSITION WHERE positionCode = :1", nativeQuery = true)
    List<Position9> findByPositionCode(String positionCode);

    //@Query(value = "SELECT * FROM POSITION WHERE positionCode LIKE :1", nativeQuery = true)
    List<Position9> findByPositionCodeLike(String positionCode);

}
