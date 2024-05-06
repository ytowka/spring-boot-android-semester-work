package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.AccountEntity
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery


@Repository
class AccountSearchRepositoryImpl(
    private val entityManager: EntityManager
) : AccountSearchRepository {

    override fun searchByLoginOrFullName(login: String, fullName: String): List<AccountEntity> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<AccountEntity> = builder.createQuery(AccountEntity::class.java)
        val root = query.from(AccountEntity::class.java)
        val loginPredicate = builder.like(root.get("login"), "%$login%")
        val fullNamePredicate = builder.like(root.get("fullName"), "%$fullName%")

        val searchPredicate = builder.or(loginPredicate, fullNamePredicate)
        query.where(searchPredicate)
        return entityManager.createQuery(query.select(root)).resultList
    }
}