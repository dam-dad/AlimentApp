package dad.alimentapp.models.app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NutritionalValues {

	private IntegerProperty kcalsTotals = new SimpleIntegerProperty();
	private IntegerProperty proteinsTotals = new SimpleIntegerProperty();
	private IntegerProperty hydratesTotals = new SimpleIntegerProperty();
	private IntegerProperty fatsTotals = new SimpleIntegerProperty();
	private IntegerProperty fibresTotals = new SimpleIntegerProperty();

	public NutritionalValues() { }
	
	public NutritionalValues(Integer kcalsTotals, Integer proteinsTotals, Integer hydratesTotals, Integer fatsTotals,
			Integer fibresTotals) {
		this.setKcalsTotals(kcalsTotals);
		this.setProteinsTotals(proteinsTotals);
		this.setHydratesTotals(hydratesTotals);
		this.setFatsTotals(fatsTotals);
		this.setFibresTotals(fibresTotals);
	}

	public final IntegerProperty kcalsTotalsProperty() {
		return this.kcalsTotals;
	}

	public final int getKcalsTotals() {
		return this.kcalsTotalsProperty().get();
	}

	public final void setKcalsTotals(final int kcalsTotals) {
		this.kcalsTotalsProperty().set(kcalsTotals);
	}

	public final IntegerProperty proteinsTotalsProperty() {
		return this.proteinsTotals;
	}

	public final int getProteinsTotals() {
		return this.proteinsTotalsProperty().get();
	}

	public final void setProteinsTotals(final int proteinsTotals) {
		this.proteinsTotalsProperty().set(proteinsTotals);
	}

	public final IntegerProperty hydratesTotalsProperty() {
		return this.hydratesTotals;
	}

	public final int getHydratesTotals() {
		return this.hydratesTotalsProperty().get();
	}

	public final void setHydratesTotals(final int hydratesTotals) {
		this.hydratesTotalsProperty().set(hydratesTotals);
	}

	public final IntegerProperty fatsTotalsProperty() {
		return this.fatsTotals;
	}

	public final int getFatsTotals() {
		return this.fatsTotalsProperty().get();
	}

	public final void setFatsTotals(final int fatsTotals) {
		this.fatsTotalsProperty().set(fatsTotals);
	}

	public final IntegerProperty fibresTotalsProperty() {
		return this.fibresTotals;
	}

	public final int getFibresTotals() {
		return this.fibresTotalsProperty().get();
	}

	public final void setFibresTotals(final int fibresTotals) {
		this.fibresTotalsProperty().set(fibresTotals);
	}
	
	public void clear() {
		this.kcalsTotals.set(0);
		this.proteinsTotals.set(0);
		this.hydratesTotals.set(0);
		this.fatsTotals.set(0);
		this.fibresTotals.set(0);
	}
}
