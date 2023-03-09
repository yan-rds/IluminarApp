package com.br.iluminar.infrastructure.firebase

import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.utils.CoroutineContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.Semaphore

class FirebaseRequestsImpl(private val coroutineContext: CoroutineContext) : FirebaseRequests {

    override val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    override val documentReference: DocumentReference?
        get() = currentUser?.uid?.let {
            FirebaseFirestore.getInstance()
                .collection("Usuarios").document(it)
        }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result =
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        user: User
    ): Resource<FirebaseUser> {
        return try {
            val result =
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            documentReference?.set(user)
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun getUserData(): Resource<UserDTO> {
        val user = UserDTO()
        val semaphore = Semaphore(0)
        documentReference?.addSnapshotListener { document, _ ->
            if (document != null) {
                user.name = document.getString("name")
                user.period = document.getString("period")
                user.schoolYear = document.get("schoolYear").toString().toInt()
                user.responsibleName = document.getString("responsibleName")
                user.responsiblePhone = document.getString("responsiblePhone")
                user.profileUri = document.getString("profileUri")
                semaphore.release()
            }
        }
        withContext(coroutineContext.io()) {
            semaphore.acquire()
        }
        return Resource.Success(user)
    }
}