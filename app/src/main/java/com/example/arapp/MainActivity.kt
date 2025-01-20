package com.example.arapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        // Set the tap listener here
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchor = hitResult.createAnchor()
            placeModel(anchor)
        }
    }

    private fun placeModel(anchor: Anchor) {
        ModelRenderable.builder()
            .setSource(this, Uri.parse("sofa.glb"))
            .build()
            .thenAccept { renderable ->
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)
                val modelNode = TransformableNode(arFragment.transformationSystem)
                modelNode.renderable = renderable
                modelNode.setParent(anchorNode)
                arFragment.arSceneView.scene.addChild(anchorNode)
            }
    }
}
