package com.example.citilin.testapp.ui.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.citilin.testapp.R;

import java.util.List;

class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contact> contacts;

    ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView contactName;
        private TextView contactPhone;

        ContactViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.tvName);
            contactPhone = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        ContactViewHolder viewHolder = (ContactViewHolder) holder;
        viewHolder.contactName.setText(contact.getName());
        viewHolder.contactPhone.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void add(Contact contact) {
        contacts.add(contact);
        notifyItemInserted(contacts.size());
    }

}
