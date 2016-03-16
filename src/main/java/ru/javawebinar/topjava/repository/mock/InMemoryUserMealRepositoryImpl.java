package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> generalRepository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (UserMeal meal :UserMealsUtil.MEAL_LIST) {
            this.save(meal,LoggedUser.id());
        }
    }
    private Map<Integer, UserMeal> getRepository(Integer userId){
       return generalRepository.get(userId);
    }

    @Override
    public UserMeal save(UserMeal userMeal,int userId) {
        Map<Integer, UserMeal> repository = getRepository(userId);
        if(repository==null)
            repository=new ConcurrentHashMap<>();
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        generalRepository.put(userId,repository);
        return userMeal;
    }

    @Override
    public boolean delete(int id,int userId) {

        Map<Integer, UserMeal> repository = getRepository(userId);
        if(repository==null)
            ExceptionUtil.check(false,id);
        //repository.remove(id);
        return generalRepository.remove(userId,repository);
    }

    @Override
    public UserMeal get(int id,int userId) {

        Map<Integer, UserMeal> repository = getRepository(userId);
        if(repository==null)
            ExceptionUtil.check(false,id);
        return repository.get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {

        Map<Integer, UserMeal> repository = getRepository(userId);
        if(repository==null)
            ExceptionUtil.check(false,"user id =" +userId);
        return repository
                .values()
                .stream()
                .sorted((o1, o2)->o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
    }
}

