package projetotcc.estudandoquimica.view.turma;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.TurmaItemBinding;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.viewmodel.TurmaViewModel;

public class TurmaAdapter extends RecyclerView.Adapter<TurmaAdapter.BindingHolder> {

    private List<Turma> turmas;
    private Context context;

    public TurmaAdapter(List<Turma> turmas, Context context) {
        this.turmas = turmas;
        this.context = context;
        addTurma(new Turma("ola"), getItemCount());
        addTurma(new Turma("ola"), getItemCount());
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final TurmaItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.turma_item,
                    parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        TurmaItemBinding binding = holder.binding;

        TurmaViewModel model = new TurmaViewModel(turmas.get(position), context);
        binding.setTurma(model);
    }

    @Override
    public int getItemCount() {
        return turmas.size();
    }

    public void addTurma(Turma turma, int posicao){
        turmas.add(posicao,turma);
        notifyItemInserted(getItemCount());
        updateTurma(posicao);
    }

    private void updateTurma(int position) {

        notifyItemChanged(position);

    }

    private void removerTurma(int position) {
        turmas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, turmas.size());
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        private TurmaItemBinding binding;

        private BindingHolder(TurmaItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }
}
