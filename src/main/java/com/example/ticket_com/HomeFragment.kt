package com.example.ticket_com

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ticket_com.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var ticketList: MutableList<Ticket>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Sapaan User
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email ?: "Traveler"
        binding.tvGreeting.text = "Hai, ${email.substringBefore("@")}!"

        dbRef = FirebaseDatabase.getInstance().getReference("tickets")
        ticketList = mutableListOf()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddEditActivity::class.java))
        }

        binding.listViewTickets.setOnItemClickListener { _, _, position, _ ->
            val ticket = ticketList[position]
            val intent = Intent(requireContext(), AddEditActivity::class.java)
            intent.putExtra("ID", ticket.id)
            intent.putExtra("DEST", ticket.destination)
            intent.putExtra("DATE", ticket.date)
            intent.putExtra("PRICE", ticket.price)
            startActivity(intent)
        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ticketList.clear()
                for (postSnapshot in snapshot.children) {
                    val ticket = postSnapshot.getValue(Ticket::class.java)
                    if (ticket != null) {
                        ticketList.add(ticket)
                    }
                }
                
                if (isAdded) {
                    val adapter = object : ArrayAdapter<Ticket>(requireContext(), R.layout.item_ticket, ticketList) {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = convertView ?: layoutInflater.inflate(R.layout.item_ticket, parent, false)
                            val item = getItem(position)
                            
                            view.findViewById<TextView>(R.id.tvDestination).text = item?.destination?.uppercase()
                            view.findViewById<TextView>(R.id.tvDate).text = item?.date
                            
                            // Format Rupiah
                            val priceVal = item?.price?.toDoubleOrNull() ?: 0.0
                            val formatRp = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                            view.findViewById<TextView>(R.id.tvPrice).text = formatRp.format(priceVal)
                            
                            return view
                        }
                    }
                    binding.listViewTickets.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}