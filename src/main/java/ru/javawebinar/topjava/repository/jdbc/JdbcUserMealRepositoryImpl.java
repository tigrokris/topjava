package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {
    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
               MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("datet_ime", userMeal.getDateTime())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());


        if (userMeal.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET description=:description, calories=:calories, " +
                            "date_time=:datetime WHERE id=:id AND user_id=:userId", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id,userId)!=0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> users = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id,userId);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time", ROW_MAPPER,userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
