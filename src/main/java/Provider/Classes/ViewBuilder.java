package Provider.Classes;

import View.Classes.ApplicationsView;
import View.Classes.CopyView;
import View.Classes.View;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewBuilder {

    public void createView(Class<? extends View> viewClass, Stream<Integer> stream, Provider provider) {
        View view = null;
        ArrayList<Integer> IDs = stream.collect(Collectors.toCollection(ArrayList::new));

        if(viewClass == CopyView.class) view = new CopyView(IDs, provider);
        else if(viewClass == ApplicationsView.class) view = new ApplicationsView(IDs, provider);

        provider.checkView(view);
        view.render();
    }
}
