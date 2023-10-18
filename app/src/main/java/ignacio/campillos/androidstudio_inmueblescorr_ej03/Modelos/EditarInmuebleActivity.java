package ignacio.campillos.androidstudio_inmueblescorr_ej03.Modelos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ignacio.campillos.androidstudio_inmueblescorr_ej03.R;
import ignacio.campillos.androidstudio_inmueblescorr_ej03.databinding.ActivityEditarInmuebleBinding;

public class EditarInmuebleActivity extends AppCompatActivity {

    private ActivityEditarInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditarInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("INMUEBLE");

        rellenarDatos(inmueble);

        binding.buttonEliminarEditarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        binding.buttonEditarEditarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble1 = crearInmueble();

                if(inmueble1 != null){
                    Intent intentNuevo = new Intent();
                    Bundle bundleNuevo = new Bundle();
                    bundleNuevo.putSerializable("INMUEBLE",inmueble1);
                    int posicion = bundle.getInt("POSICION");


                    intentNuevo.putExtras(bundleNuevo);
                    setResult(RESULT_OK,intentNuevo);
                    finish();
                }else{
                    Toast.makeText(EditarInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Inmueble crearInmueble() {
        if (binding.editTextDireccionEditarInmueble.getText().toString().isEmpty()
                || binding.editTextNumEditarInmueble.getText().toString().isEmpty()
                || binding.editTextCiudadEditarInmueble.getText().toString().isEmpty()
                || binding.editTextProvinciaEditarInmueble.getText().toString().isEmpty()
                || binding.editTextCPEditarInmueble.getText().toString().isEmpty()
                || binding.ratingBarValoracionEditarInmueble.getRating() < 0.5){
            return null;
        }


        return new Inmueble(
                binding.editTextDireccionEditarInmueble.getText().toString(),
                Integer.parseInt(binding.editTextNumEditarInmueble.getText().toString()),
                binding.editTextCiudadEditarInmueble.getText().toString(),
                binding.editTextProvinciaEditarInmueble.getText().toString(),
                binding.editTextCPEditarInmueble.getText().toString(),
                binding.ratingBarValoracionEditarInmueble.getRating()
        );
    }

    private void rellenarDatos(Inmueble inmueble) {
        binding.editTextDireccionEditarInmueble.setText(inmueble.getDireccion());
        binding.editTextNumEditarInmueble.setText((String.valueOf(inmueble.getNumero())));
        binding.editTextCiudadEditarInmueble.setText(inmueble.getCiudad());
        binding.editTextProvinciaEditarInmueble.setText(inmueble.getProvincia());
        binding.editTextCPEditarInmueble.setText(inmueble.getCp());
        binding.ratingBarValoracionEditarInmueble.setRating(inmueble.getValoracion());
    }
}