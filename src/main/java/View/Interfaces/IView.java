package View.Interfaces;

public interface IView {

	void render();

	default void reRender(){
		System.out.print("\033[H\033[2J");
		this.render();
	};

}