package com.paragon.utils.imagepicker.async;

import android.content.Intent;
import android.os.AsyncTask;

import com.paragon.utils.R;
import com.paragon.utils.imagepicker.bean.PickResult;
import com.paragon.utils.imagepicker.bundle.PickSetup;
import com.paragon.utils.imagepicker.enums.EPickType;
import com.paragon.utils.imagepicker.img.ImageHandler;
import com.paragon.utils.imagepicker.resolver.IntentResolver;

import java.lang.ref.WeakReference;

public class AsyncImageResult extends AsyncTask<Intent, Void, PickResult> {

    private final WeakReference<IntentResolver> weakIntentResolver;
    private final WeakReference<PickSetup> weakSetup;
    private OnFinish onFinish;

    public AsyncImageResult(IntentResolver intentResolver, PickSetup setup) {
        this.weakIntentResolver = new WeakReference<>(intentResolver);
        this.weakSetup = new WeakReference<>(setup);
    }

    public AsyncImageResult setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    @Override
    protected PickResult doInBackground(Intent... intents) {

        //Create a PickResult instance
        PickResult result = new PickResult();

        IntentResolver resolver = weakIntentResolver.get();

        if (resolver == null) {
            result.setError(new Error(resolver.getActivity().getString(R.string.activity_destroyed)));
            return result;
        }

        try {
            //Get the data intent from onActivityResult()
            Intent data = intents[0];

            //Define if it was pick from camera
            boolean fromCamera = resolver.fromCamera(data);

            //Instance of a helper class
            ImageHandler imageHandler = ImageHandler
                    .with(resolver.getActivity()).setup(weakSetup.get())
                    .provider(fromCamera ? EPickType.CAMERA : EPickType.GALLERY)
                    .uri(fromCamera ? resolver.cameraUri() : data.getData());

            //Setting uri and path for result
            result.setUri(imageHandler.getUri())
                    .setPath(imageHandler.getUriPath())
                    .setBitmap(imageHandler.decode());

            if(fromCamera)
                result.setPickType(EPickType.CAMERA);
            else
                result.setPickType(EPickType.GALLERY);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e);
            return result;
        }
    }


    @Override
    protected void onPostExecute(PickResult r) {
        if (onFinish != null)
            onFinish.onFinish(r);
    }

    public interface OnFinish {
        void onFinish(PickResult pickResult);
    }

}
