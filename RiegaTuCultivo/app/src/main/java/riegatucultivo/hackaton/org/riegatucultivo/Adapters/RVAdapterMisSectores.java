package riegatucultivo.hackaton.org.riegatucultivo.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import riegatucultivo.hackaton.org.riegatucultivo.Modelo.Sector;
import riegatucultivo.hackaton.org.riegatucultivo.R;
import riegatucultivo.hackaton.org.riegatucultivo.Tools.SelectableAdapter;

/**
 * Created by Sebas on 19/08/2015.
 */
public class RVAdapterMisSectores extends SelectableAdapter<RVAdapterMisSectores.ViewHolder> {

    @SuppressWarnings("unused")
    private final String TAG = RVAdapterMisSectores.class.getSimpleName();
    ArrayList<Sector> sectores;
    private ViewHolder.ClickListener clickListener;

    public RVAdapterMisSectores(ArrayList<Sector> nSecores, ViewHolder.ClickListener clickListener){
        super();
        sectores = nSecores;
        this.clickListener = clickListener;
    }

    public void addAll(ArrayList<Sector> nSectores){
        sectores.clear();
        sectores.addAll(nSectores);
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getIdItems(){
        if (getSelectedItems() != null && getSelectedItems().size() > 0){
            ArrayList<Integer> seleccionados = (ArrayList<Integer>) getSelectedItems();
            ArrayList<Integer> idServiciosElminarBD = new ArrayList<>();
            while ( ! seleccionados.isEmpty()){
                idServiciosElminarBD.add( sectores.get( seleccionados.get(0) ).getIdSector() );
                seleccionados.remove(0);
            }

            return idServiciosElminarBD;
        }

        return null;
    }
    public void removeItem(int position) {
        sectores.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

            // Split the list in ranges
            while (!positions.isEmpty()) {
                removeItem(positions.get(0));
                positions.remove(0);
            }
    }

    @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sector, parent, false);
            ViewHolder pvh = new ViewHolder(v, clickListener);
            return pvh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.nombre_sector.setText(sectores.get(position).getNombreSector());
            holder.tipoCultivo.setImageResource(sectores.get(position).getImagenTipo());
            // Highlight the item if it's selected
            holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        }

        @Override
        public int getItemCount() {
            return sectores.size();
        }

    public Sector getServicio(int position) {
        return sectores.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ClickListener listener;
        private View selectedOverlay;
        private CardView cv_servicio;
        private TextView nombre_sector;
        private ImageView tipoCultivo;
        private Sector mServicio;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            nombre_sector = (TextView) itemView.findViewById(R.id.it_nombre_sector);
            tipoCultivo = (ImageView) itemView.findViewById(R.id.it_tipo_cultivo);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
             void onItemClicked(int position);

             boolean onItemLongClicked(int position);
        }


    }
}
