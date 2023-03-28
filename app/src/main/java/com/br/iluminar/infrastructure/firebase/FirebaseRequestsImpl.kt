package com.br.iluminar.infrastructure.firebase

import android.util.Log
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.model.Task
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

    override suspend fun getTasksList(): List<Task> {
        val tasks = mutableListOf<Task>()

        val tasksCollection =
            FirebaseFirestore.getInstance().collection("Atividades").document().get()
        Log.i("doc", tasksCollection.toString())

        FirebaseFirestore.getInstance().collection("Atividades")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                snapshot?.documents?.forEach { document ->
                    val task = document.toObject(Task::class.java)
                    if (task != null) {
                        tasks.add(task)
                    }
                }

            }
        return tasks
    }

    override suspend fun addTask(task: Task): Resource<Unit> {
        return try {
            FirebaseFirestore.getInstance().collection("Atividades").add(task).await()
            Resource.Success(Unit)
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun addMessage(message: Message): Resource<Unit> {
        return try {
            FirebaseFirestore.getInstance().collection("Mensagens").add(message).await()
            Resource.Success(Unit)
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUserId(): Resource<String?> {
        val userId = currentUser?.uid
        return Resource.Success(userId)
    }


}