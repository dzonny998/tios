import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Sud } from 'src/app/models/sud';
import { SudService } from 'src/app/services/sud.service';

@Component({
  selector: 'app-sud-dialog',
  templateUrl: './sud-dialog.component.html',
  styleUrls: ['./sud-dialog.component.css']
})
export class SudDialogComponent {
  public flag!: number;

  constructor(
    public snackBar: MatSnackBar,
    public sudService: SudService,
    @Inject(MAT_DIALOG_DATA) public dataSud: Sud,
    public dialogRef: MatDialogRef<SudDialogComponent>
  ) { }

  public add(): void {
    console.log("ID je " + this.dataSud.id + this.dataSud.naziv);
    this.sudService.addSud(this.dataSud).subscribe(() => {
      this.snackBar.open('Uspesno dodat sud: ' + this.dataSud.naziv, 'OK', {
        duration: 2500
      });
    },
    (error: Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open('Doslo je do greske prilikom dodavanja novog suda. ', 'Zatvori', {
        duration: 2500
      });
    });
  }

  public update(): void {
    this.sudService.updateSud(this.dataSud).subscribe(() => {
      this.snackBar.open('Uspesno izmenjen sud: ' + this.dataSud.naziv, 'OK', {
        duration: 2500
      });
    },
    (error: Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open('Doslo je do greske prilikom izmene suda. ', 'Zatvori', {
        duration: 2500
      });
    });
  }

  public delete(): void {
    this.sudService.deleteSud(this.dataSud.id).subscribe(res => {
      this.snackBar.open('Uspesno obrisan sud', 'OK', {
        duration: 2500
      });
    },
    (error: Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open('Doslo je do greske prilikom brisanja suda. ', 'Zatvori', {
        duration: 2500
      });
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od izmene. ', 'Zatvori', {
      duration: 1000
    });
  }
}
