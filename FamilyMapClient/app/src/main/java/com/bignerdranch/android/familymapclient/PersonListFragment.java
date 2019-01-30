package com.bignerdranch.android.familymapclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

public class PersonListFragment extends Fragment {
    private PersonAdapter mAdapter;
    private RecyclerView mPersonRecyclerView;
    private TextView personFragmentTitle;
    private ImageView personFragmentImage;
    private ConstraintLayout personFragmentHeader;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String personID = bundle.getString("personID");
        View v = inflater.inflate(R.layout.fragment_person_list, container, false);

        mPersonRecyclerView = v.findViewById(R.id.person_recycler_view);
        personFragmentTitle = v.findViewById(R.id.person_fragment_title_text);
        personFragmentImage = v.findViewById(R.id.person_fragment_title_image);
        personFragmentHeader = v.findViewById(R.id.person_list_title);



        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        personFragmentTitle.setText(R.string.person_fragment_title);
        final Drawable markerUp = new IconDrawable(getActivity(), FontAwesomeIcons.fa_arrow_up).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        final Drawable markerDown = new IconDrawable(getActivity(), FontAwesomeIcons.fa_arrow_down).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        personFragmentImage.setImageDrawable(markerUp);

        personFragmentHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPersonRecyclerView.getAlpha() == 1) {
                    mPersonRecyclerView.setAlpha(0);
                    personFragmentImage.setImageDrawable(markerDown);
                }
                else {
                    mPersonRecyclerView.setAlpha(1);
                    personFragmentImage.setImageDrawable(markerUp);
                }
            }
        });


        updateUI(personID);

        return v;
    }
    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView personListInfo;
        private ImageView personImage;
        private String mRelative;
        private String mGender;
        private String mPersonID;


        public void bind(String relative) {
            String[] strings = relative.split("/");
            mGender = strings[0];
            mPersonID = strings[1];
            mRelative = strings[2];
            personListInfo.setText(mRelative);
            if (mGender.equals("m")) {
                Drawable genderIcon =  new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.maleIcon).sizeDp(40);
                personImage.setImageDrawable(genderIcon);
            }
            else if (mGender.equals("f")) {
                Drawable genderIcon =  new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                        colorRes(R.color.femaleIcon).sizeDp(40);
                personImage.setImageDrawable(genderIcon);
            }
        }
        public PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_person, parent, false));
            itemView.setOnClickListener(this);

            personImage = itemView.findViewById(R.id.person_list_image);
            personListInfo = itemView.findViewById(R.id.person_list_info);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PersonActivity.class);
            intent.putExtra("EXTRA_PERSON", mPersonID);
            startActivity(intent);
        }
    }
    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {
        ArrayList mRelatives;
        public PersonAdapter(ArrayList relatives) {
            mRelatives = relatives;
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PersonHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            String relative = mRelatives.get(position).toString();
            holder.bind(relative);
        }
        @Override
        public int getItemCount() {
            return mRelatives.size();
        }
    }
    private void updateUI(String ID) {
        String personID = ID;
        ArrayList relatives = Storage.getInstance().getRelatives(personID);

        mAdapter = new PersonAdapter(relatives);
        mPersonRecyclerView.setAdapter(mAdapter);
    }
}
