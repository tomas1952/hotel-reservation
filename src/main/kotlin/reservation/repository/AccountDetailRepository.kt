package reservation.repository

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

    override fun findAll(): ArrayList<AccountDetail> {
        return ArrayList(repository.values.toList())
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    fun findAllByName(name: String) : ArrayList<AccountDetail> {
        val result = repository
            .map { it.value }
            .filter { it.name == name }

        return ArrayList(result)
    }
}