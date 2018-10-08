package projetotcc.estudandoquimica.view.compartilhado;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.ComentarioItemBinding;
import projetotcc.estudandoquimica.model.Comentario;
import projetotcc.estudandoquimica.viewmodel.ComentarioViewModel;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {

    private List<Comentario> comentarios;
    private ClickComentarioListener clickComentarioListener;

    public ComentarioAdapter(ClickComentarioListener clickComentarioListener) {

        this.clickComentarioListener = clickComentarioListener;
        this.comentarios = new ArrayList<>();
    }

    public void setComentarios(List<Comentario> comentarios){

        if(this.comentarios == null) {
            this.comentarios = comentarios;
            notifyDataSetChanged();
        }
    }

    public void addComentario(Comentario comentario){

        comentarios.add(comentario);
        notifyItemInserted(getItemCount());
        notifyItemRangeChanged(0, getItemCount());

    }

    public void addComentario(Comentario comentario, int posicao){

        comentarios.add(posicao, comentario);
        notifyItemInserted(posicao);
        notifyItemRangeChanged(posicao, getItemCount());

    }

    public void removerComentario(int position){

        comentarios.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, getItemCount());
    }

    public void updateComentario(int position){


        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());

    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ComentarioItemBinding  binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.comentario_item, parent, false);

        return new ComentarioViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {

        ComentarioItemBinding binding = holder.binding;

        ComentarioViewModel viewModel = new ComentarioViewModel();
        viewModel.setComentario(comentarios.get(position));

        binding.setComentario(viewModel);

    }

    @Override
    public int getItemCount() {
        return comentarios == null ? 0 : comentarios.size();
    }

    public class ComentarioViewHolder extends ViewHolder{

        ComentarioItemBinding binding;

        public ComentarioViewHolder(ComentarioItemBinding binding) {
            super(binding.cardView);

            this.binding = binding;
            this.binding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickComentarioListener.onPressComentario( binding.foto,
                            comentarios.get(getAdapterPosition()), getAdapterPosition());
                    return false;
                }
            });

        }
    }

    public interface ClickComentarioListener{

        void onPressComentario(View v, Comentario comentario, int posicao);
    }

}
