package com.example.gta_geo_torpedoassault.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.gta_geo_torpedoassault.R;

/**
 * Classe utilitaire pour l'accès à l'éxécution des permissions.
 */
public abstract class PermissionUtils {

    /**
     * Demande une permission.
     * Si une explication supplémentaire doit être montrée :
     * Affiche une boîte de dialogue qui déclenche la demande.
     */
    public static void requestPermission(AppCompatActivity activity, int requestId,
                                         String permission, boolean finishActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Afficher la boite de dialogue.
            PermissionUtils.RationaleDialog.newInstance(requestId, finishActivity)
                    .show(activity.getSupportFragmentManager(), "dialog");
        } else {
            // L'autorisation de localisation n'a pas encore été accordée, -> la demander.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);

        }
    }

    /**
     * Vérifie si la permission a été accordée.
     * @see androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    /**
     * Demande les permissions d'accès à la localisation.
     */
    public static void requestLocationPermissions(AppCompatActivity activity, int requestId,
                                                  boolean finishActivity) {
        if (ActivityCompat
                .shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Afficher la boite de dialogue.
            PermissionUtils.RationaleDialog.newInstance(requestId, finishActivity)
                    .show(activity.getSupportFragmentManager(), "dialog");
        } else {
            // L'autorisation de localisation n'a pas encore été accordée, -> la demander.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    requestId);
        }
    }

    /**
     * Une boite de dialogue qui affiche un message d'explication et déclenche la demande de permission.
     */
    public static class PermissionDeniedDialog extends DialogFragment {

        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

        private boolean finishActivity = false;

        /**
         * Creates a new instance of this dialog and optionally finishes the calling Activity
         * when the 'Ok' button is clicked.
         */
        public static PermissionDeniedDialog newInstance(boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);

            PermissionDeniedDialog dialog = new PermissionDeniedDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            finishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.location_permission_denied)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(getActivity(), R.string.permission_required_toast,
                        Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }

    /**
     * A dialog that explains the use of the location permission and requests the necessary
     * permission.
     * <p>
     * The activity should implement
     * {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}
     * to handle permit or denial of this permission request.
     */
    public static class RationaleDialog extends DialogFragment {

        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

        private boolean finishActivity = false;

        /**
         * Creates a new instance of a dialog displaying the rationale for the use of the location
         * permission.
         * <p>
         * The permission is requested after clicking 'ok'.
         *
         * @param requestCode    Id of the request that is used to request the permission. It is
         *                       returned to the
         *                       {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}.
         * @param finishActivity Whether the calling Activity should be finished if the dialog is
         *                       cancelled.
         */
        public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
            RationaleDialog dialog = new RationaleDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            finishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_rationale_location)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        // After click on Ok, request the permission.
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                requestCode);
                        // Do not finish the Activity while requesting permission.
                        finishActivity = false;
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(getActivity(),
                                R.string.permission_required_toast,
                                Toast.LENGTH_SHORT)
                        .show();
                getActivity().finish();
            }
        }
    }
}
