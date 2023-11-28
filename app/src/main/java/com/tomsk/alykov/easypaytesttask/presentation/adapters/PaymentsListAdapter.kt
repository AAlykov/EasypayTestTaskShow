package com.tomsk.alykov.easypaytesttask.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tomsk.alykov.easypaytesttask.R
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentModel
import com.tomsk.alykov.easypaytesttask.databinding.PaymentItemBinding
import com.tomsk.alykov.easypaytesttask.utils.Utils

class PaymentsListAdapter(val context: Context) :
    RecyclerView.Adapter<PaymentsListAdapter.PaymentViewHolder>() {

    var paymentsList = listOf<PaymentModel>()
        set(value) {
            val callback = PaymentsDiffCallback(paymentsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    class PaymentViewHolder(val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            PaymentItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int = paymentsList.size

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = paymentsList[position]
        val noData = context.getString(R.string.no_data)

        if (payment.id != null
            && payment.id.toString().isNotEmpty()
            && payment.id.toString().isDigitsOnly()) {
            holder.binding.idTextView.text = payment.id.toString()
        } else {
            holder.binding.idTextView.text = noData
        }

        if (payment.title != null
            && payment.title.toString().isNotEmpty()) {
            holder.binding.titleTextView.text = payment.title.toString()
        } else {
            holder.binding.titleTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.error_nodata_color
                ))
            holder.binding.titleTextView.text = noData
        }

        if (payment.amount != null
            && payment.amount.toString().isNotEmpty()
            && payment.amount.toString().toDoubleOrNull() != null
            //смотря что подразумеваем под количеством, можно проверять различными методами
            //&& payment.amount.toString().toIntOrNull() != null
            //&& payment.amount.toString().all { char -> char.isDigit() }
        ) {
            holder.binding.amountTextView.text = payment.amount.toString()
        } else {
            holder.binding.amountTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.error_nodata_color
                ))
            holder.binding.amountTextView.text = noData
        }

        if (payment.created != null
            && payment.created.toString().isNotEmpty()
            && payment.created.toString().toLongOrNull() != null
        ) {
            holder.binding.createdTextView.text = Utils.timeStampToString(payment.created.toLong())
        } else {
            holder.binding.createdTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.error_nodata_color
                ))
            holder.binding.createdTextView.text = noData
        }
    }

}