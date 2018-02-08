package nathan.ui
import nathan.util.implicitUtil._
import com.thoughtworks.binding.{dom, _}
import org.scalajs.dom._

object NavBar {
   @dom def nav:Binding[Node] = {
    <nav class="navbar navbar-inverse navbar-static-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Cluster Monitor System</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav navbar-left">
            <li>
              <a href="#">数据报表</a>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data:data-toggle="dropdown" data:role="button">Agent Machine
                <span
                class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a href="addAgent.html">添加Agent</a>
                  <a href="showAgent.html">查看Agent</a>
                </li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data:data-toggle="dropdown" data:role="button">告警
                <span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a href="#">计划告警</a>
                </li>
                <li>
                  <a href="#">实时告警</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  }
}