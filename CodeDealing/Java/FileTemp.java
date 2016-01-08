<%@ Control Language="VB" AutoEventWireup="false" CodeFile="WebUserControl.ascx.vb" Inherits="_35seater_WebUserControl" %>
<%@ Register assembly="AjaxControlToolkit" namespace="AjaxControlToolkit" tagprefix="cc1" %>
       <div id="s" style="width: 499px">
            <asp:Panel ID="Panel1" runat="server" Width="77px">
                <table class="style66">
                    <tr>
                        <td class="style68" rowspan="2">
                            <asp:Image ID="Image1" runat="server" ImageUrl="~/images/streer.gif" />
                        </td>
                        <td rowspan="5" style="background-color: #E4E4E4">
                            &amp;nbsp;</td>
                        <td>
                            &amp;nbsp;</td>
                        <td>
                            <span class="style106">
                            <asp:CheckBox ID="CheckBox1" runat="server" ForeColor="White" Height="33px" 
                                Text="1" Width="33px" />
                            <cc1:ToggleButtonExtender ID="CheckBox1_ToggleButtonExtender" runat="server" 
                                CheckedImageUrl="images/selected.gif" 
                                DisabledUncheckedImageUrl="images/disabled.gif" Enabled="True" ImageHeight="33" 
                                ImageWidth="33" TargetControlID="CheckBox1" 
                                UncheckedImageUrl="images/booked.gif"></cc1:ToggleButtonExtender>
                            </span>
                        </td>
                        <td>
                            <span class="style106">
                            <asp:CheckBox ID="CheckBox3" runat="server" ForeColor="White" Height="33px" 
                                Text="3" Width="33px" />
                            <cc1:ToggleButtonExtender ID="CheckBox3_ToggleButtonExtender" runat="server" 
                                CheckedImageUrl="images/selected.gif" 
                                DisabledUncheckedImageUrl="images/disabled.gif" Enabled="True" ImageHeight="33" 
                                ImageWidth="33" TargetControlID="CheckBox3" 
                                UncheckedImageUrl="images/booked.gif"></cc1:ToggleButtonExtender>
                            </span>
                        </td>
                        <td>
                            <span class="style106">
                            <asp:CheckBox ID="CheckBox10" runat="server" ForeColor="White" Height="33px" 
                                Text="10" Width="33px" />
                            <cc1:ToggleButtonExtender ID="CheckBox10_ToggleButtonExtender" runat="server" 
                                CheckedImageUrl="images/selected.gif" 
                                DisabledUncheckedImageUrl="images/disabled.gif" Enabled="True" ImageHeight="33" 
                                ImageWidth="33" TargetControlID="CheckBox10" 
                                UncheckedImageUrl="images/booked.gif"></cc1:ToggleButtonExtender>
                            </span>
                        </td>
                        <td>
                                                  </tr>
                </table>
                <hr style="width: 494px; height: -15px" />
                <table class="style99" style="text-align: center; height: 10px;">
                    <tr class="style102">
                        <td class="style103" style="text-align: center; " colspan="3">
                            <table class="style113">
                                <tr>
                                    <td class="style116">
                                        <asp:Label ID="Label20" runat="server" style="font-weight: 700; color: #990000; font-family: Arial, Helvetica, sans-serif; font-size: small;" 
                                            Text="Select Boarding Point :"></asp:Label>
                                    </td>
                                    <td class="style115">
                                        <asp:Label ID="Label23" runat="server" 
                                            style="font-weight: 700; color: #990000; font-family: Arial, Helvetica, sans-serif; font-size: small;" 
                                            Text="Select Dropping Point :"></asp:Label>
                                        <asp:Label ID="Label24" runat="server" ForeColor="White" 
                                            style="font-size: xx-small" Text="Label"></asp:Label>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="style114" style="text-align: right">
                                        <asp:DropDownList ID="DropDownList1" runat="server" AppendDataBoundItems="True" 
                                            DataSourceID="SqlDataSource1" DataTextField="bd_time" DataValueField="bd_time" 
                                            style="margin-left: 0px; " Width="240px">
                                            <asp:ListItem>Choose...</asp:ListItem>
                                        </asp:DropDownList>
                                        <asp:SqlDataSource ID="SqlDataSource1" runat="server" 
                                            ConnectionString="<%$ ConnectionStrings:ConnectionString %>" 
                                            SelectCommand="SELECT DISTINCT [bd_time] FROM [boardingpt] WHERE ([service] = @service)">
                                            <SelectParameters>
                                                <asp:ControlParameter ControlID="Label22" Name="service" PropertyName="Text" 
                                                    Type="String" />
                                            </SelectParameters>
                                        </asp:SqlDataSource>
                                    </td>
                                    <td>
                                        <asp:DropDownList ID="DropDownList2" runat="server" AppendDataBoundItems="True" 
                                            DataSourceID="SqlDataSource2" DataTextField="dp_time" DataValueField="dp_time" 
                                            style="margin-left: 0px; " Width="240px">
                                            <asp:ListItem>Choose...</asp:ListItem>
                                        </asp:DropDownList>
                                        <asp:SqlDataSource ID="SqlDataSource2" runat="server" 
                                            ConnectionString="<%$ ConnectionStrings:ConnectionString %>" 
                                            SelectCommand="SELECT DISTINCT [dp_time] FROM [droppingpt] WHERE ([service] = @service)">
                                            <SelectParameters>
                                                <asp:ControlParameter ControlID="Label22" Name="service" PropertyName="Text" 
                                                    Type="String" />
                                            </SelectParameters>
                                        </asp:SqlDataSource>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="style114" colspan="2" style="text-align: center; padding-top: 12px;">
                                        <asp:ImageButton ID="ImageButton1"  OnClientClick ="return checkboxChecked();" runat="server" 
                                            ImageUrl="~/images/redContinueButton.gif" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="style114" colspan="2" style="text-align: left">
                                        <div style="visibility: hidden; overflow: hidden">
                                            <asp:CheckBoxList ID="CheckBoxList1" runat="server" 
                                                DataSourceID="SqlDataSource4" DataTextField="sel_seat" 
                                                DataValueField="sel_seat" Font-Size="Smaller" ForeColor="Maroon" 
                                                RepeatDirection="Horizontal" RepeatLayout="Flow">
                                            </asp:CheckBoxList>
                                            <br />
                                            <asp:CheckBoxList ID="CheckBoxList2" runat="server" 
                                                DataSourceID="SqlDataSource3" DataTextField="seats" DataValueField="seats" 
                                                Font-Size="Smaller" ForeColor="Maroon" RepeatDirection="Horizontal" 
                                                RepeatLayout="Flow">
                                            </asp:CheckBoxList>
                                            <asp:SqlDataSource ID="SqlDataSource3" runat="server" 
                                                ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="SELECT TOP 1 [seats], [id], [dates], [service]
FROM            a1_holds
WHERE        (dates = @dates) AND (service = @service) ORDER BY [id] DESC">
                                                <SelectParameters>
                                                    <asp:ControlParameter ControlID="Label25" Name="dates" PropertyName="Text" />
                                                    <asp:ControlParameter ControlID="Label22" Name="service" PropertyName="Text" />
                                                </SelectParameters>
                                            </asp:SqlDataSource>
                                            <asp:SqlDataSource ID="SqlDataSource4" runat="server" 
                                                ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="SELECT DISTINCT sel_seat
FROM            a1_ticket
WHERE        (journey_date = @journey_date) AND (serv_code = @serv_code)">
                                                <SelectParameters>
                                                    <asp:ControlParameter ControlID="Label25" Name="journey_date" 
                                                        PropertyName="Text" />
                                                    <asp:ControlParameter ControlID="Label22" Name="serv_code" 
                                                        PropertyName="Text" />
                                                </SelectParameters>
                                            </asp:SqlDataSource>
                                            <asp:Label ID="Label25" runat="server" Font-Size="Smaller" Text="Label"></asp:Label>
                                            <asp:Label ID="Label26" runat="server" Font-Size="Smaller" Text="Label"></asp:Label>
                                            <asp:Label ID="Label27" runat="server" Font-Size="Smaller"></asp:Label>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr class="style102">
                        <td class="style100">
                            <asp:TextBox ID="TextBox1" runat="server" BorderColor="White" 
                                BorderStyle="None" BorderWidth="0px" ReadOnly="True" 

                                style="font-size: small; text-align: center; font-weight: 700; color: #FFFFFF;"></asp:TextBox>
                        </td>
                        <td class="style112">
                            <asp:Label ID="Label17" runat="server" 
                                style="font-size: small; font-weight: 700; color: #FFFFFF;"></asp:Label>
                        </td>
                        <td class="style110">
                            <asp:Label ID="Label18" runat="server" 
                                style="font-size: small; font-weight: 700; color: #FFFFFF;"></asp:Label>
                        </td>
                    </tr>
                </table>
            </asp:Panel>
</div>

<script type="text/javascript" language="javascript">
function checkboxChecked(){
    var checked=false;
    var allInputs = document.getElementsByTagName("input");
    var noOfChkBox = 0; // number of check box
    var noOfDisabledChkBox = 0; // number of disabled check box

    for(var i=0; i<allInputs.length; i++){
         var chk=allInputs[i];
         if(chk.type == "checkbox"){
            noOfChkBox++;
            if(chk.disabled){
                noOfDisabledChkBox++;
            }

            if(chk.checked){
               checked=true;
               break;
            }
         } 
    }
    if(noOfDisabledChkBox===noOfChkBox)
    {
        checked=true; 
    }
    if(!checked)
        alert("OOps! You haven't select checkboxes");
    return checked;
}
</script>

