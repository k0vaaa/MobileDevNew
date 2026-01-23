package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.tensorflow.lite.task.vision.classifier.Classifications;
import org.tensorflow.lite.task.vision.classifier.ImageClassifier;
import org.tensorflow.lite.support.image.TensorImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.mirea.kovalikaa.pocketdictionary.R;

public class SearchFragment extends Fragment {

    private MainViewModel viewModel;
    private EditText editTextWord;
    private TextView textViewResult;
    private ImageView headerImageView;
    private boolean isUserImage = false;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        headerImageView.setImageBitmap(imageBitmap);
                        isUserImage = true;
                        classifyImage(imageBitmap);
                    }
                }
            }
    );

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                        Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                        headerImageView.setImageBitmap(imageBitmap);
                        isUserImage = true;
                        classifyImage(imageBitmap);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    showImageSourceDialog();
                } else {
                    Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new MainViewModelFactory(requireContext()))
                .get(MainViewModel.class);

        headerImageView = view.findViewById(R.id.headerImageView);
        editTextWord = view.findViewById(R.id.editTextWord);
        textViewResult = view.findViewById(R.id.textViewResult);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Button buttonSave = view.findViewById(R.id.buttonSave);
        Button buttonCamera = view.findViewById(R.id.buttonCamera);

        if (viewModel.getCurrentUserEmail() == null) {
            buttonSave.setVisibility(View.GONE);
        } else {
            buttonSave.setVisibility(View.VISIBLE);
        }

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            isUserImage = false;
            viewModel.searchWord(word);
        });

        buttonSave.setOnClickListener(v -> {
            viewModel.saveCurrentWord();
        });

        buttonCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showImageSourceDialog();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        viewModel.wordDefinition.observe(getViewLifecycleOwner(), definition -> {
            if (definition != null) {
                String resultText = definition.getDefinition();
                textViewResult.setText(resultText);

                String imageUrl = definition.getImageUrl();
                if (!isUserImage) {
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_error)
                                .error(R.drawable.ic_error)
                                .into(headerImageView);
                    }
                }
            }
        });

        viewModel.toastMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Image Source");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else if (which == 1) {
                openGallery();
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            cameraLauncher.launch(takePictureIntent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Unable to open camera on this device", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private void classifyImage(Bitmap bitmap) {
        try {
            ImageClassifier.ImageClassifierOptions options = ImageClassifier.ImageClassifierOptions.builder()
                    .setMaxResults(1)
                    .build();

            ImageClassifier classifier = ImageClassifier.createFromFileAndOptions(
                    requireContext(), "mobilenet.tflite", options);

            TensorImage image = TensorImage.fromBitmap(bitmap);
            List<Classifications> results = classifier.classify(image);

            if (!results.isEmpty() && !results.get(0).getCategories().isEmpty()) {
                String detectedLabel = results.get(0).getCategories().get(0).getLabel();

                if (detectedLabel.contains(",")) {
                    detectedLabel = detectedLabel.split(",")[0];
                }

                Toast.makeText(getContext(), "AI detected: " + detectedLabel, Toast.LENGTH_LONG).show();
                editTextWord.setText(detectedLabel);
                viewModel.searchWord(detectedLabel);
            } else {
                Toast.makeText(getContext(), "Could not recognize object", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error init TFLite: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}