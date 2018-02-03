package nathan

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import nathan.util.implicitDecoder._
import org.scalajs.dom.html._
import org.scalajs.dom.{Event, document, html}

import scala.scalajs.js.annotation.JSExport

@JSExport
object WebApp {


  @JSExport
  def fun() = {
    dom.render(document.body, genRender())
  }

  @dom
  def genRender(): Binding[html.Div] = {
    val tags = Vars("init-1", "init-2")
    <div>
      {tagPicker(tags).bind}<ol>
      {for (tag <- tags) yield <li>
        {tag}
      </li>}
    </ol>
    </div>
  }

  @dom
  def tagPicker(tags: Vars[String]): Binding[html.Div] = {
    val input = {
      <input type="text"></input>
    }.as[Input]
    val addHandler = { _: Event => {
      input.value match {
        case str: String if str.trim != "" && !tags.value.contains(input.value) =>
          tags.value += input.value
          input.value = ""
        case _ =>

      }
    }
    }
    <div>
      <div>
        {tags.map { tag =>
        <p>
          {tag}<button onclick={_: Event => tags.value -= tag}>x</button>
        </p>
      }}
      </div>
      <div>
        {input}<button onclick={addHandler}>Add</button>
      </div>
    </div>
  }
}