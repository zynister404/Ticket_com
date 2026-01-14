package com.example.ticket_com

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.ticket_com.databinding.ActivityAddEditBinding
import com.google.firebase.database.FirebaseDatabase

@Suppress("SameParameterValue")
class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private var ticketId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("ID")) {
            ticketId = intent.getStringExtra("ID")
            binding.etDestination.setText(intent.getStringExtra("DEST"))
            binding.etDate.setText(intent.getStringExtra("DATE"))
            binding.etPrice.setText(intent.getStringExtra("PRICE"))
            binding.btnSave.text = "Update Tiket"
            binding.btnDelete.visibility = View.VISIBLE
        }

        binding.btnSave.setOnClickListener {
            saveTicket()
        }
        
        binding.btnDelete.setOnClickListener {
            deleteTicket()
        }
    }

    private fun saveTicket() {
        val dest = binding.etDestination.text.toString()
        val date = binding.etDate.text.toString()
        val price = binding.etPrice.text.toString()

        if (dest.isEmpty()) return

        val ref = FirebaseDatabase.getInstance().getReference("tickets")
        
        if (ticketId == null) {
            val newId = ref.push().key
            val ticket = Ticket(newId, dest, date, price)
            if (newId != null) {
                ref.child(newId).setValue(ticket).addOnCompleteListener {
                    showNotification("Booking Berhasil", "Tiket ke $dest berhasil dipesan.")
                    finish()
                }
            }
        } else {
            val ticket = Ticket(ticketId, dest, date, price)
            ref.child(ticketId!!).setValue(ticket).addOnCompleteListener {
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    
    private fun deleteTicket() {
        if(ticketId != null) {
            FirebaseDatabase.getInstance().getReference("tickets").child(ticketId!!).removeValue()
            finish()
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "ticket_channel"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Ticket Notif", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        manager.notify(1, builder.build())
    }
}