package reservation.repository

import common.exception.NotFoundResourceException
import reservation.entity.AccountDetail

class AccountDetailRepository: BaseRepository<AccountDetail> {
    private var currentId: Long = 0L
    private val repository: HashMap<Long, AccountDetail> = hashMapOf()
    override fun insert(resource: AccountDetail): AccountDetail {
        resource.id = ++currentId
        repository[currentId] = resource
        return resource
    }

    override fun update(id: Long, resource: AccountDetail): AccountDetail {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): AccountDetail {
        val result = repository.values.filter { it.id == id }
        if (result.isEmpty())
            throw NotFoundResourceException("입출금내역")

        return result.first()
    }

    override fun findAll(): ArrayList<AccountDetail> {
        return ArrayList(repository.values.toList())
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun clearAll() {
        repository.clear()
    }

    fun findAllByName(name: String) : ArrayList<AccountDetail> {
        val result = repository
            .map { it.value }
            .filter { it.name == name }

        return ArrayList(result)
    }
}