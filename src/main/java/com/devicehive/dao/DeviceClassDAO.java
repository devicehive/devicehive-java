package com.devicehive.dao;

import com.devicehive.configuration.Constants;
import com.devicehive.exceptions.dao.NoSuchRecordException;
import com.devicehive.model.DeviceClass;
import com.devicehive.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO JavaDoc
 */

@Stateless
public class DeviceClassDAO {

    private static int DEFAULT_TAKE = 1000;

    @PersistenceContext(unitName = Constants.PERSISTENCE_UNIT)
    private EntityManager em;



    public List<DeviceClass> getDeviceClassList(String name, String namePattern, String version, String sortField,
                                                Boolean sortOrderAsc, Integer take, Integer skip) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<DeviceClass> criteria = criteriaBuilder.createQuery(DeviceClass.class);
        Root from = criteria.from(DeviceClass.class);

        List<Predicate> predicates = new ArrayList<>();

        if (namePattern != null) {
            predicates.add(criteriaBuilder.like(from.get("name"), namePattern));
        } else {
            if (name != null) {
                predicates.add(criteriaBuilder.equal(from.get("name"), name));
            }
        }
        if (version != null) {
            predicates.add(criteriaBuilder.equal(from.get("version"), version));
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        if (sortField != null) {
            if (sortOrderAsc == null || sortOrderAsc) {
                criteria.orderBy(criteriaBuilder.asc(from.get(sortField)));
            } else {
                criteria.orderBy(criteriaBuilder.desc(from.get(sortField)));
            }
        }

        TypedQuery<DeviceClass> resultQuery = em.createQuery(criteria);

        if (skip != null) {
            resultQuery.setFirstResult(skip);
        }
        if (take == null) {
            take = DEFAULT_TAKE;
        }
        resultQuery.setMaxResults(take);
        return resultQuery.getResultList();
    }

    public DeviceClass get(@NotNull long id){
        return em.find(DeviceClass.class,id);
    }

    public void delete(@NotNull long id) {
        DeviceClass dc = em.find(DeviceClass.class,id);
        if( dc == null ) {
            throw new NoSuchRecordException("There is no DeviceClass entity with id '" + id + "'");
        }
        try{
            em.remove(dc);
        }catch (Throwable e){
            throw e;
        }
    }

    public DeviceClass getWithEquipment(@NotNull long id){
        TypedQuery<DeviceClass> tq = em.createNamedQuery("DeviceClass.getWithEquipment",DeviceClass.class);
        tq.setParameter("id",id);
        List<DeviceClass> result = tq.getResultList();
        return result.isEmpty()?null:result.get(0);
    }

    public List<DeviceClass> getList() {
        return em.createQuery("select dc from DeviceClass dc").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceClass getDeviceClass(long id) {
        return em.find(DeviceClass.class, id);
    }

    public void saveDeviceClass(DeviceClass deviceClass){
        em.merge(deviceClass);
    }

    public DeviceClass getDeviceClassByNameAndVersion(String name, String version) {
        TypedQuery<DeviceClass> query = em.createNamedQuery("DeviceClass.findByNameAndVersion", DeviceClass.class);
        query.setParameter("version", version);
        query.setParameter("name", name);
        List<DeviceClass> result = query.getResultList();
        return  result.isEmpty() ? null : result.get(0);
    }

    public DeviceClass addDeviceClass(DeviceClass deviceClass) {
        return em.merge(deviceClass);
    }


}
