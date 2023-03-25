package DB

import Models.Hayvon

interface DbService {
    fun addLabel(hayvon: Hayvon)
    fun editLabel(hayvon: Hayvon):Int
    fun deleteLabel(hayvon: Hayvon)
    fun getAllLabel():ArrayList<Hayvon>
}
