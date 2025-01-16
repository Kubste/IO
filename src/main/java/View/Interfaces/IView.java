package View.Interfaces;

public interface IView {

	void render() throws Exception;

	default void reRender() throws Exception {
		System.out.print("\033[H\033[2J");
		this.render();
	};

}