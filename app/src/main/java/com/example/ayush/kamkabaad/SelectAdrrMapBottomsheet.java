package com.example.ayush.kamkabaad;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;

import butterknife.ButterKnife;

public class SelectAdrrMapBottomsheet extends BottomSheetDialogFragment implements OnMapReadyCallback,
        TomtomMapCallback.OnMapLongClickListener {

    private TomtomMap tomtomMap;
    private SearchApi searchApi;
    private Route route;
    private ImageButton btnClear;
    private Dialog dialogInProgress;
    private Context context , myContext;
    BottomSheetDialog d;
    String address;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.addr_map_bottomshhet, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        myContext = (FragmentActivity) getActivity();
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                d = (BottomSheetDialog) dialog;
                View bottomSheetInternal = d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        initMapServices();
        initUIView();
        setUIViewListeners();
        disableSearchButtons();

         return rootView;
    }


    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        this.tomtomMap.setMyLocationEnabled(true);
        this.tomtomMap.addOnMapLongClickListener(this);
        this.tomtomMap.getMarkerSettings().setMarkersClustering(true);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        showDialogInProgress();
        handleLongClick(latLng);
    }

    private void initMapServices() {
        MapFragment mapFragment = (MapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getAsyncMap(this);
        searchApi = OnlineSearchApi.create(context);
    }

    private void initUIView() {
        btnClear = d.findViewById(R.id.btn_main_clear);
        dialogInProgress = new Dialog(context);
        dialogInProgress.setContentView(R.layout.dialog_in_progress);
        dialogInProgress.setCanceledOnTouchOutside(true);
    }

    private void setUIViewListeners() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMap();
            }
        });
    }

    private boolean isMapCleared() {
        return route == null;
    }

    private void clearMap() {
        tomtomMap.clear();
        route = null;
        disableSearchButtons();
    }

    private void handleApiError(Throwable e) {
        dismissDialogInProgress();
        Toast.makeText(context, getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
    }

    private void disableSearchButtons() {
        btnClear.setEnabled(false);
    }

    private void showDialogInProgress() {
        if(!dialogInProgress.isShowing()) {
            dialogInProgress.show();
        }
    }

    private void dismissDialogInProgress() {
        if(dialogInProgress.isShowing()) {
            dialogInProgress.dismiss();
        }
    }

    private void createMarkerIfNotPresent(LatLng position, Icon icon) {
        Optional<Marker> optionalMarker = tomtomMap.findMarkerByPosition(position);
        if (!optionalMarker.isPresent()) {
            tomtomMap.addMarker(new MarkerBuilder(position)
                    .icon(icon));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void handleLongClick(@NonNull LatLng latLng) {

        //TODO :    Apply Reverse Geocoding Api To Get Location

    }

    }
