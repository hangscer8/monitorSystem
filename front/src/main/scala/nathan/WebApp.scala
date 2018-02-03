package nathan

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import org.scalajs.dom.html._
import org.scalajs.dom.{Event, Node, document, window}

import scala.scalajs.js.annotation.JSExport

@JSExport
object WebApp {
  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]

  @JSExport
  def fun() = {
    dom.render(document.body, bindingTable(data))
  }

  @dom
  def bindingTable(contacts:BindingSeq[Contact]):Binding[Table]={
    <table>
      <tbody>
        {
        contacts.map{contact=>bindingTr(contact)}
        }
      </tbody>
    </table>
  }

  @dom
  def 修改联系人信息的按钮(contact: Contact): Binding[Button] = {
    <button onclick={event: Event => {
      contact.name.value = contact.name.value + 123
    }}>
    </button>
  }

  @dom
  def bindingTr(contact: Contact): Binding[TableRow] = {
    <tr>
      <td>
        {contact.name.bind}
      </td>
      <td>
        {contact.email.bind}
      </td>
      <td>
        {修改联系人信息的按钮(contact).bind}
      </td>
    </tr>
  }
}