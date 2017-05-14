package city.commerce.repository;

import city.commerce.entity.DivisionEntity;
import city.commerce.repository.mapper.DivisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Repository
public class DivisionRepository {
    @Autowired
    private DivisionMapper divisionMapper;

    public DivisionEntity queryDivision(int id) {
        return divisionMapper.find(id);
    }

    public DivisionEntity queryDivisionByName(String name) {
        return divisionMapper.findByName(name);
    }

    public DivisionEntity addDivision(DivisionEntity divisionEntity) {
        divisionMapper.add(divisionEntity);
        return divisionEntity;
    }

    public DivisionEntity updateDivision(DivisionEntity divisionEntity) {
        divisionMapper.update(divisionEntity);
        return divisionEntity;
    }

    public List<DivisionEntity> queryAllDivisions() {
        return divisionMapper.all();
    }

    public void deleteDivision(int divisionId) {
        divisionMapper.delete(divisionId);
    }
}
