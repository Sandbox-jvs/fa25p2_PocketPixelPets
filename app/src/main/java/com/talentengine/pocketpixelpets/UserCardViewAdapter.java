/**
 * Adapter class to convert the UserCardView data and adapt it onto the recycler view
 * @author Cristian Perez
 * @since 12/17/25
 */

package com.talentengine.pocketpixelpets;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class UserCardViewAdapter extends RecyclerView.Adapter<UserCardViewAdapter.UserViewHolder> {
    // The list of card items in the recycler
    private ArrayList<UserCardView> cardList;

    // The interface functionality for deleting a user which will be handled out of this class
    private OnUserDeleteListener deleteListener;

    public UserCardViewAdapter(ArrayList<UserCardView> cardList, OnUserDeleteListener listener) {
        this.cardList = cardList;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new UserViewHolder(view);
    }

    // Update all the values with the current user values
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserCardView userCardView = cardList.get(position);
        holder.username.setText(userCardView.getUsername());
        holder.petName.setText(userCardView.getPetName());
        holder.firstAction.setText(userCardView.getFirstAction());
        holder.secondAction.setText(userCardView.getSecondAction());
        holder.thirdAction.setText(userCardView.getThirdAction());

        holder.petColorImage.setImageResource(userCardView.getColorRes());

        // Setup onClickListeners for the Delete User button and pass the functionality to the activity
        holder.deleteButton.setOnClickListener(v -> {
            // Previously had issues due to information not being updated, so this ensures
            // that the current position of the recycler is the same as what's being passed along
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && deleteListener != null) {
                deleteListener.onDelete(cardList.get(position), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView petName;
        TextView firstAction;
        TextView secondAction;
        TextView thirdAction;
        ImageView petColorImage;
        MaterialButton deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userNameTextView);
            petName = itemView.findViewById(R.id.petNameTextView);
            firstAction = itemView.findViewById(R.id.firstNewestActionTextView);
            secondAction = itemView.findViewById(R.id.secondNewestActionTextView);
            thirdAction = itemView.findViewById(R.id.thirdNewestActionTextView);
            petColorImage = itemView.findViewById(R.id.petColorImageView);
            deleteButton = itemView.findViewById(R.id.deleteUserButton);
        }
    }

    /**
     * Used to pass the functionality of deleting a user in the Admin Activity
     */
    public interface OnUserDeleteListener {
        void onDelete(UserCardView user, int position);
    }
}