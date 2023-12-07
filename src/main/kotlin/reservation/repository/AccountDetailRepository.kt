package reservation.repository

import reservation.entity.AccountDetail

class AccountDetailRepository: BaseRepository<AccountDetail> {
    override fun add(resource: AccountDetail): AccountDetail {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, resource: AccountDetail): AccountDetail {
        TODO("Not yet implemented")
    }

    override fun findAll(): ArrayList<AccountDetail> {
        TODO("Not yet implemented")
    }

    override fun remove(id: Long) {
        TODO("Not yet implemented")
    }
}